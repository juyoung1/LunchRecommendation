package com.example.lunchrecommendation.view.menu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.component.GridLayoutItemDecoration
import com.example.lunchrecommendation.data.dao.MenuDao
import com.example.lunchrecommendation.databinding.FrgMenuListBinding
import com.example.lunchrecommendation.view.adapter.MenuListAdapter
import com.example.lunchrecommendation.view.util.MenuListUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * 홈 - 마이 페이지
 */
@AndroidEntryPoint
class FrgJapanFood : BaseFragment<FrgMenuListBinding>() {

    // 메뉴 리스트 어댑터
    private lateinit var mAdapter: MenuListAdapter

    // 메뉴 리스트 데이터
    private val mData: ArrayList<MenuDao> = ArrayList()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgMenuListBinding.inflate(inflater, container, false)

    override fun initData() {

        mData.clear()
        mData.addAll(MenuListUtil.japanFood())
    }

    override fun initView() { initRecyclerView() }

    /**
     * RecyclerView 초기화
     */
    private fun initRecyclerView() {

        context?.let { ctx ->

            with(mBinding) {

                /** 메뉴 리스트 */
                rvList.apply {

                    val spanCount = 2
                    val itemGap = 5

                    mAdapter = MenuListAdapter(ctx, mData)
                    layoutManager = GridLayoutManager(ctx, spanCount)
                    adapter = mAdapter
                    addItemDecoration(GridLayoutItemDecoration(context, itemGap, itemGap, spanCount))
                    isNestedScrollingEnabled = true
                    mAdapter.selectItem = object : MenuListAdapter.SelectItem {

                        override fun selectItem(position: Int) {

                            if (mData.size > position) {

                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun newInstance(): FrgJapanFood {
            return FrgJapanFood().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}