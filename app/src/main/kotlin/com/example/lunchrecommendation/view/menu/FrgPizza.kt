package com.example.lunchrecommendation.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import com.example.lunchrecommendation.databinding.FrgPizzaBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메뉴 탭 - 피자
 */
@AndroidEntryPoint
class FrgPizza : BaseFragment<FrgPizzaBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgPizzaBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {

        fun newInstance(): FrgPizza {
            return FrgPizza().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}