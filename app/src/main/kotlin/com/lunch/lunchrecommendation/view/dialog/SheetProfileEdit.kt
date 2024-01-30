package com.lunch.lunchrecommendation.view.dialog

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.lunch.lunchrecommendation.base.BaseBottomSheetFragment
import com.lunch.lunchrecommendation.util.PreferencesUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lunch.lunchrecommendation.R
import com.lunch.lunchrecommendation.databinding.SheetProfileEditBinding

/**
 * 프로필 수정 바텀시트
 */
class SheetProfileEdit(val function: () -> Unit) : BaseBottomSheetFragment<SheetProfileEditBinding>() {

    private var nickName = ""           // 닉네임
    private var favoriteFood = ""       // 최애 음식
    private var profileImage = ""       // 프로필 이미지

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = SheetProfileEditBinding.inflate(inflater, container, false)

    override fun initData() {}

    override fun initView() {

        initDisplay()
        setClickListener()
        initEditText()
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
     * Initialize Display
     */
    private fun initDisplay() {

        context?.let { ctx ->

            with(mBinding) {

                etFavoriteFood.hint = getString(R.string.sheet_text_9, PreferencesUtil.getPreferencesString("nickName"))

                // 프로필 이미지
                if (PreferencesUtil.getPreferencesString("profileImage").isNotEmpty()) {

                    ivNoProfile.visibility = View.GONE
                    Glide.with(ctx).load(PreferencesUtil.getPreferencesString("profileImage")).into(ivProfile)
                }
            }
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            /** 닫기 */
            tvClose.setOnClickListener { dismiss() }

            /** 저장 */
            tvSave.setOnClickListener {

                nickName = etNickName.text.toString().trim()
                favoriteFood = etFavoriteFood.text.toString().trim()

                // 닉네임 비었을 시 경고 문구 노출
                if (nickName.isEmpty()) {

                    tvWarningA.visibility = View.VISIBLE
                    tvWarningA.text = getString(R.string.sheet_text_10)

                } else {

                    // 프로필 이미지 있으면 이미지 저장
                    if (profileImage.isNotEmpty()) {

                        PreferencesUtil.setPreferencesString("profileImage", profileImage)

                        // 기본 프로필 변경 시 기존 저장된 이미지 제거
                    } else if (ivNoProfile.visibility == View.VISIBLE) {

                        PreferencesUtil.deletePreferences("profileImage")
                    }
                    PreferencesUtil.setPreferencesString("nickName", nickName)
                    PreferencesUtil.setPreferencesString("favoriteFood", favoriteFood)
                    function()
                    dismiss()
                }
            }

            /** 프로필 이미지 */
            cvProfile.setOnClickListener {

                val sheetCameraGallery = SheetCameraGallery { imagePath ->

                    profileImage = imagePath

                    // 전달받은 프로필 이미지 없는 경우 ivNoProfile 노출
                    if (profileImage.isNotEmpty()) {

                        ivNoProfile.visibility = View.GONE
                        ivProfile.visibility = View.VISIBLE
                        ivProfile.setImageURI(Uri.parse(profileImage))

                    } else {

                        ivNoProfile.visibility = View.VISIBLE
                        ivProfile.visibility = View.GONE
                    }
                }
                sheetCameraGallery.show(childFragmentManager, "SheetCameraGallery")
            }
        }
    }

    /**
     * Edit Text 초기화
     */
    private fun initEditText() {
        with(mBinding) {

            // 닉네임
            etNickName.apply {

                setText(PreferencesUtil.getPreferencesString("nickName"))

                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {

                        nickName = etNickName.text.toString().trim()

                        // 닉네임 입력 8글자로 제한
                        if (nickName.length > 8) {
                            etNickName.setText(nickName.substring(0, 8))
                            etNickName.setSelection(8)
                            tvWarningA.visibility = View.VISIBLE
                        } else {
                            tvWarningA.visibility = View.GONE
                        }
                    }
                })
            }

            // 최애 음식
            etFavoriteFood.apply {

                setText(PreferencesUtil.getPreferencesString("favoriteFood"))

                addTextChangedListener(object : TextWatcher {

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {

                        favoriteFood = etFavoriteFood.text.toString().trim()

                        // 최애 음식 입력 8글자로 제한
                        if (favoriteFood.length > 8) {
                            etFavoriteFood.setText(favoriteFood.substring(0, 8))
                            etFavoriteFood.setSelection(8)
                            tvWarningB.visibility = View.VISIBLE
                        } else {
                            tvWarningB.visibility = View.GONE
                        }
                    }
                })
            }
        }
    }
}