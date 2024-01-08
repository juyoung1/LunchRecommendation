package com.example.lunchrecommendation.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgKoreanFoodBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메뉴 탭 - 한식
 */
@AndroidEntryPoint
class FrgKoreaFood : BaseFragment<FrgKoreanFoodBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgKoreanFoodBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {

        fun newInstance(): FrgKoreaFood {
            return FrgKoreaFood().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}