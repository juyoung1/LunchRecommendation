package com.example.lunchrecommendation.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgKoreanFoodBinding
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import com.example.lunchrecommendation.databinding.FrgSnackBarBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메뉴 탭 - 분식
 */
@AndroidEntryPoint
class FrgSnackBar : BaseFragment<FrgSnackBarBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgSnackBarBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {

        fun newInstance(): FrgSnackBar {
            return FrgSnackBar().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}