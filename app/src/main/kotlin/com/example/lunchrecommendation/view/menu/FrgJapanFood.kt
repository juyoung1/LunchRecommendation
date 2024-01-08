package com.example.lunchrecommendation.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgJapanFoodBinding
import com.example.lunchrecommendation.databinding.FrgKoreanFoodBinding
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 홈 - 마이 페이지
 */
@AndroidEntryPoint
class FrgJapanFood : BaseFragment<FrgJapanFoodBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgJapanFoodBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {

        fun newInstance(): FrgJapanFood {
            return FrgJapanFood().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}