package com.example.lunchrecommendation.view.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import com.example.lunchrecommendation.util.PreferencesUtil
import com.example.lunchrecommendation.view.dialog.SheetProfileEdit
import com.example.lunchrecommendation.view.mypage.activity.ActAppInfo
import com.example.lunchrecommendation.view.mypage.activity.ActMyLikeFoodList
import com.example.lunchrecommendation.view.mypage.activity.ActTakePictureFood
import com.example.lunchrecommendation.view.nickname.ActNickName

/**
 * 홈 - 마이 페이지
 */
class FrgMyPage : BaseFragment<FrgMyPageBinding>() {

    // 프로필 편집 바텀시트
    private var sheetProfileEdit: SheetProfileEdit? = null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgMyPageBinding.inflate(inflater, container, false)

    override fun initData() {}

    override fun initView() {

        initDisplay()
        setClickListener()
    }

    /**
     * Initialize Display
     */
    private fun initDisplay() {

        context?.let { ctx ->

            with(mBinding) {

                incMyFavorite.tvTitle.text = getString(R.string.home_text_3)
                incMyFood.tvTitle.text = getString(R.string.home_text_4)
                incAppInfo.tvTitle.text = getString(R.string.home_text_5)

                // 닉네임
                tvNickName.text = PreferencesUtil.getPreferencesString("nickName")

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

        context?.let { ctx ->

            with(mBinding) {

                /** 프로필 수정 */
                clProfileEdit.setOnClickListener {

                    sheetProfileEdit?.dismiss()
                    sheetProfileEdit = SheetProfileEdit {
                        tvNickName.text = PreferencesUtil.getPreferencesString("nickName")

                        // 프로필 기본 이미지 일 때 ivNoProfile 노출
                        if (PreferencesUtil.getPreferencesString("profileImage").isNotEmpty()) {

                            ivProfile.visibility = View.VISIBLE
                            ivNoProfile.visibility = View.GONE
                            Glide.with(ctx).load(PreferencesUtil.getPreferencesString("profileImage")).into(ivProfile)

                        } else {

                            ivProfile.visibility = View.GONE
                            ivNoProfile.visibility = View.VISIBLE
                        }
                    }
                    sheetProfileEdit?.show(parentFragmentManager, "")
                }

                /** 내 찜 목록 */
                incMyFavorite.clItem.setOnClickListener {

                    Intent(context, ActMyLikeFoodList::class.java).apply {

                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(this)
                    }
                }

                /** 내가 찍은 음식 */
                incMyFood.clItem.setOnClickListener {

                    Intent(context, ActTakePictureFood::class.java).apply {

                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(this)
                    }
                }

                /** 앱 정보 */
                incAppInfo.clItem.setOnClickListener {

                    Intent(context, ActAppInfo::class.java).apply {

                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(this)
                    }
                }

                /** 내정보 초기화 */
                tvReset.setOnClickListener {

                    openPopup(getString(R.string.popup_text_1), getString(R.string.popup_text_4), getString(R.string.popup_text_2), {}, getString(R.string.confirm),
                        {
                            PreferencesUtil.deletePreferences("nickNameView")
                            PreferencesUtil.deletePreferences("nickName")
                            PreferencesUtil.deletePreferences("favoriteFood")
                            PreferencesUtil.deletePreferences("profileImage")
                            PreferencesUtil.deletePreferencesStringSet("myLikeFood")
                            PreferencesUtil.deletePreferencesStringSet("saveFoodPhotos")
                            moveToNickName()
                        },
                        false
                    )
                }
            }
        }
    }

    /**
     * 닉네임 입력 화면 이동
     */
    private fun moveToNickName() {

        Intent(context, ActNickName::class.java).apply {

            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(this)
            activity?.finish()
        }
    }

    companion object {

        fun newInstance(): FrgMyPage {
            return FrgMyPage().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}