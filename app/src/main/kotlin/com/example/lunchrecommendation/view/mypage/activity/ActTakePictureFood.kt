package com.example.lunchrecommendation.view.mypage.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseContractActivity
import com.example.lunchrecommendation.component.GridLayoutItemDecoration
import com.example.lunchrecommendation.data.dao.MenuDao
import com.example.lunchrecommendation.databinding.ActTakePictureFoodBinding
import com.example.lunchrecommendation.util.CommonUtils
import com.example.lunchrecommendation.util.PreferencesUtil
import com.example.lunchrecommendation.view.adapter.TakePictureFoodListAdapter
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 마이 페이지 - 내가 찍은 음식
 */
class ActTakePictureFood : BaseContractActivity<ActTakePictureFoodBinding>() {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1001
        private const val GALLERY_PERMISSION_REQUEST_CODE = 1002
    }

    // 찍었던 사진 보여주기
    private val savedFoodPhotos = PreferencesUtil.getPreferencesStringSet("saveFoodPhotos")

    private lateinit var currentPhotoPath: String

    // 내 찜 목록 리스트 어댑터
    private lateinit var mAdapter: TakePictureFoodListAdapter
    private val mData: ArrayList<MenuDao> = ArrayList()

    override fun getViewBinding() = ActTakePictureFoodBinding.inflate(layoutInflater)

    override fun initData() {

        slideAnimation = true
        fullScreen = false
        bgStatusBar = R.color.color_f5f5f5

        // 찍었던 사진 보여주기
        for (foodPhotos in savedFoodPhotos) {
            mData.add(MenuDao(menuImage = foodPhotos))
        }
    }

    override fun initView() {
        CommonUtils.log(localClassName)

        initDisplay()
        setClickListener()
        initRecyclerView()
    }

    /**
     * Initialize Display
     */
    private fun initDisplay() {

        with(mBinding) {

            // 헤더
            incHeader.tvTitle.text = getString(R.string.home_text_4)

            // 찍은 음식 이미지 없을 시 노출
            noPhotoVisibility()
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            /** 뒤로 가기 */
            incHeader.ivBack.setOnClickListener { onBackPressed() }

            /** 사진 촬영 */
            tvCamera.setOnClickListener {

                if (ContextCompat.checkSelfPermission(this@ActTakePictureFood, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    openCamera()

                } else {

                    ActivityCompat.requestPermissions(this@ActTakePictureFood, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
                }
            }

            /** 앨범에서 선택 */
            tvAlbum.setOnClickListener {

                val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }

                if (ContextCompat.checkSelfPermission(this@ActTakePictureFood, galleryPermission) == PackageManager.PERMISSION_GRANTED) {

                    // 앨범 실행
                    openGallery()

                } else {

                    // 앨범 권한 요청
                    ActivityCompat.requestPermissions(this@ActTakePictureFood, arrayOf(galleryPermission), GALLERY_PERMISSION_REQUEST_CODE)
                }
            }
        }
    }

    /**
     * RecyclerView 초기화
     */
    private fun initRecyclerView() {

        with(mBinding) {

            /** 내가 찍은 사진 리스트 */
            rvList.apply {

                val spanCount = 3
                val itemGap = 3

                mAdapter = TakePictureFoodListAdapter(this@ActTakePictureFood, mData)
                layoutManager = GridLayoutManager(this@ActTakePictureFood, spanCount)
                adapter = mAdapter
                addItemDecoration(GridLayoutItemDecoration(context, itemGap, itemGap, spanCount))
                mAdapter.selectItem = object : TakePictureFoodListAdapter.SelectItem {

                    override fun selectItem(position: Int) {

                        if (mData.size > position) {

                            mAdapter.removePhoto(position)
                        }
                    }
                }
            }
        }
    }

    /** 찍은 사진 없을 시 문구 노출 */
    fun noPhotoVisibility() {

        mBinding.clNoFoodPhoto.visibility = if (mData.isEmpty()) View.VISIBLE else View.GONE
    }

    /** 카메라 실행 */
    private fun openCamera() {

        val photoFile: File? = createImageFile()

        photoFile?.let { file ->

            val photoUri: Uri = FileProvider.getUriForFile(this@ActTakePictureFood, "com.example.lunchrecommendation.fileProvider", file)
            takePicture.launch(photoUri)
        }
    }

    /** 이미지 파일 생성 */
    private fun createImageFile(): File? {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = this@ActTakePictureFood.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFileName = "JPEG_${timeStamp}_"

        return File.createTempFile(imageFileName, ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    /** 사진 촬영 */
    private val takePicture: ActivityResultLauncher<Uri> = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            mAdapter.addPhoto(Uri.fromFile(File(currentPhotoPath)))
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
            data!!.data?.let {
                mAdapter.addPhoto(it)
            }
        }
    }
}