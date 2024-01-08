package com.example.lunchrecommendation.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgFastFoodBinding
import com.example.lunchrecommendation.databinding.FrgKoreanFoodBinding
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메뉴 탭 - 패스트 푸드
 */
@AndroidEntryPoint
class FrgFastFood : BaseFragment<FrgFastFoodBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgFastFoodBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {

        fun newInstance(): FrgFastFood {
            return FrgFastFood().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}