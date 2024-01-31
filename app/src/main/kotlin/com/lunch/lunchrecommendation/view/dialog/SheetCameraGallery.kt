package com.lunch.lunchrecommendation.view.dialog

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.lunch.lunchrecommendation.base.BaseBottomSheetFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lunch.lunchrecommendation.R
import com.lunch.lunchrecommendation.databinding.SheetCameraGalleryBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 사진 촬영, 앨범에서 선택 바텀시트
 */
class SheetCameraGallery(private val imageCapturedCallback: (String) -> Unit) : BaseBottomSheetFragment<SheetCameraGalleryBinding>(){

    private lateinit var currentPhotoPath: String
    private var imageUri: Uri? = null

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
        private const val GALLERY_PERMISSION_REQUEST_CODE = 1002
    }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        SheetCameraGalleryBinding.inflate(inflater, container, false)

    override fun initData() {
        arguments?.let { }
    }

    override fun initView() {

        setOnClickListener()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomModalDialogTheme)

        bottomSheetDialog.setOnShowListener {
            val bottomSheet = (it as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            BottomSheetBehavior.from((bottomSheet as FrameLayout)).state = BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet).isHideable = true
            BottomSheetBehavior.from(bottomSheet).isDraggable = false
        }
        return bottomSheetDialog
    }

    /**
     * Click Listener
     */
    private fun setOnClickListener() {
        context?.let { ctx ->

            with(mBinding) {

                /** 닫기 */
                ivClose.setOnClickListener { dismiss() }

                /** 사진 촬영 */
                clPhoto.setOnClickListener {

                    if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                        openCamera()

                    } else {

                        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
                    }
                }

                /** 앨범에서 선택 */
                clGallery.setOnClickListener {
                    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Manifest.permission.READ_MEDIA_IMAGES
                        } else {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        }

                    if (ContextCompat.checkSelfPermission(ctx, galleryPermission) == PackageManager.PERMISSION_GRANTED) {

                        // 앨범 실행
                        openGallery()

                    } else {

                        // 앨범 권한 요청
                        ActivityCompat.requestPermissions(requireActivity(), arrayOf(galleryPermission), GALLERY_PERMISSION_REQUEST_CODE)
                    }
                }

                /** 기본 이미지로 변경 */
                tvBasicImage.setOnClickListener {

                    imageCapturedCallback("")
                    dismiss()
                }
            }
        }
    }

    /** 카메라 실행 */
    private fun openCamera() {

        val photoFile: File? = createImageFile()

        photoFile?.let { file ->

            val photoUri: Uri = FileProvider.getUriForFile(requireContext(), "com.lunch.lunchrecommendation.fileProvider", file)
            takePicture.launch(photoUri)
        }
    }

    /** 이미지 파일 생성 */
    private fun createImageFile(): File? {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFileName = "JPEG_${timeStamp}_"

        return File.createTempFile(imageFileName, ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    /** 사진 촬영 */
    private val takePicture: ActivityResultLauncher<Uri> = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->

        if (success) {

            imageCapturedCallback(currentPhotoPath)
            dismiss()
        }
    }

    /** 갤러리 열기 */
    private fun openGallery() {

        val gallery = Intent(Intent.ACTION_PICK)

        gallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        pickImageLauncher.launch(gallery)
    }

    /** 이미지 선택 런처 */
    private val pickImageLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            data!!.data.let {
                imageUri = it
                imageCapturedCallback(imageUri.toString())
            }
            dismiss()
        }
    }
}
