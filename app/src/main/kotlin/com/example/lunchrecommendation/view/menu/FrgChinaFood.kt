package com.example.lunchrecommendation.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgChinaFoodBinding
import com.example.lunchrecommendation.databinding.FrgKoreanFoodBinding
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메뉴 탭 - 중식
 */
@AndroidEntryPoint
class FrgChinaFood : BaseFragment<FrgChinaFoodBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgChinaFoodBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {

        fun newInstance(): FrgChinaFood {
            return FrgChinaFood().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}