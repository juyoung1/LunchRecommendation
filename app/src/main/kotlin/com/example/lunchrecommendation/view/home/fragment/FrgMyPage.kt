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
import com.example.lunchrecommendation.view.mypage.ActMyLikeFoodList
import com.example.lunchrecommendation.view.mypage.ActTakePictureFood
import com.example.lunchrecommendation.view.nickname.ActNickName
import dagger.hilt.android.AndroidEntryPoint

/**
 * 홈 - 마이 페이지
 */
@AndroidEntryPoint
class FrgMyPage : BaseFragment<FrgMyPageBinding>() {

    // 프로필 편집 바텀시트
    private var sheetProfileEdit: SheetProfileEdit? = null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgMyPageBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {

        initDisplay()
        setClickListener()
    }

    /**
     * Initialize Display
     */
    private fun initDisplay() {

        with(mBinding) {

            incMyFavorite.tvTitle.text = getString(R.string.home_text_3)
            incMyFood.tvTitle.text = getString(R.string.home_text_4)
            incAppInfo.tvTitle.text = getString(R.string.home_text_5)

            // 닉네임
            tvNickName.text = PreferencesUtil.getPreferencesString("nickName")

            // 프로필 이미지
            if (PreferencesUtil.getPreferencesString("profileImage").isNotEmpty()) {

                ivNoProfile.visibility = View.GONE
                Glide.with(requireContext()).load(PreferencesUtil.getPreferencesString("profileImage")).into(ivProfile)
            }
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            // 프로필 수정
            clProfileEdit.setOnClickListener {

                sheetProfileEdit?.dismiss()
                sheetProfileEdit = SheetProfileEdit {
                    tvNickName.text = PreferencesUtil.getPreferencesString("nickName")
                    Glide.with(requireContext()).load(PreferencesUtil.getPreferencesString("profileImage")).into(ivProfile)
                }
                sheetProfileEdit?.show(parentFragmentManager, "")
            }

            // 내 찜 목록
            incMyFavorite.clItem.setOnClickListener {

                Intent(context, ActMyLikeFoodList::class.java).apply {

                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                }
            }

            // 내가 찍은 음식
            incMyFood.clItem.setOnClickListener {

                Intent(context, ActTakePictureFood::class.java).apply {

                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                }
            }

            // 내정보 초기화
            tvReset.setOnClickListener {

                openPopup(getString(R.string.popup_text_1), getString(R.string.popup_text_4), getString(R.string.popup_text_2), {}, getString(R.string.confirm),
                    {
                        PreferencesUtil.deletePreferences("nickName")
                        PreferencesUtil.deletePreferences("favoriteFood")
                        PreferencesUtil.deletePreferences("profileImage")
                        PreferencesUtil.deletePreferencesStringSet("myLikeFood")
                        moveToNickName()
                    },
                    false
                )
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