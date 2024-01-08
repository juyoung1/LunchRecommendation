package com.example.lunchrecommendation.view.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgChickenBinding
import com.example.lunchrecommendation.databinding.FrgMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 메뉴 탭 - 치킨
 */
@AndroidEntryPoint
class FrgChicken : BaseFragment<FrgChickenBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgChickenBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    companion object {

        fun newInstance(): FrgChicken {
            return FrgChicken().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}