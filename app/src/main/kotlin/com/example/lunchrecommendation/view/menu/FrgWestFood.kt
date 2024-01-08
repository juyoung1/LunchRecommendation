package com.example.lunchrecommendation.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgJapanFoodBinding
import com.example.lunchrecommendation.databinding.FrgKoreanFoodBinding
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import com.example.lunchrecommendation.databinding.FrgWestFoodBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메뉴 탭 - 양식
 */
@AndroidEntryPoint
class FrgWestFood : BaseFragment<FrgWestFoodBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgWestFoodBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {

        fun newInstance(): FrgWestFood {
            return FrgWestFood().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}