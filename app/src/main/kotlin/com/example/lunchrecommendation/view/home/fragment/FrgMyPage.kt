package com.example.lunchrecommendation.view.home.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import com.example.lunchrecommendation.util.PreferencesUtil
import com.example.lunchrecommendation.view.dialog.SheetProfileEdit
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

            tvNickName.text = PreferencesUtil.getPreferencesString("nickName")

            incMyFavorite.tvTitle.text = getString(R.string.home_text_3)
            incMyFood.tvTitle.text = getString(R.string.home_text_4)
            incAppInfo.tvTitle.text = getString(R.string.home_text_5)
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            clProfileEdit.setOnClickListener {

                sheetProfileEdit?.dismiss()
                sheetProfileEdit = SheetProfileEdit { tvNickName.text = PreferencesUtil.getPreferencesString("nickName") }
                sheetProfileEdit?.show(parentFragmentManager, "")
            }
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