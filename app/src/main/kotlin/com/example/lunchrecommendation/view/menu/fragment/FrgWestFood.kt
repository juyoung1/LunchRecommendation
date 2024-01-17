package com.example.lunchrecommendation.view.menu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.component.GridLayoutItemDecoration
import com.example.lunchrecommendation.data.dao.MenuDao
import com.example.lunchrecommendation.databinding.FrgMenuListBinding
import com.example.lunchrecommendation.util.PreferencesUtil
import com.example.lunchrecommendation.view.adapter.MenuListAdapter
import com.example.lunchrecommendation.view.home.activity.ActHome
import com.example.lunchrecommendation.view.util.MenuListUtil

/**
 * 메뉴 탭 - 양식
 */
class FrgWestFood : BaseFragment<FrgMenuListBinding>() {

    // 메뉴 리스트 어댑터
    private lateinit var mAdapter: MenuListAdapter

    // 메뉴 리스트 데이터
    private val mData: ArrayList<MenuDao> = ArrayList()

    // 내가 찜했던 메뉴
    private val myLikedFood = PreferencesUtil.getPreferencesStringSet("myLikeFood")

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgMenuListBinding.inflate(inflater, container, false)

    override fun initData() {

        mData.clear()
        mData.addAll(MenuListUtil.westFood())

        for (item in mData) {
            item.isSelected = myLikedFood.contains(item.menuImage.toString())
        }
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
                    mAdapter.selectItem = object : MenuListAdapter.SelectItem {

                        override fun selectItem(position: Int) {

                            if (mData.size > position) {

                                val selectItem = mData[position]

                                // 찜 하기
                                selectItem.isSelected = !selectItem.isSelected

                                // 찜 선택 토스트
                                (activity as ActHome).showToast(selectItem.isSelected)

                                // 찜한 이미지 PreferenceUtil 에 저장, 다른 프래그먼트에서 찜한 이미지에 추가하여 저장
                                val existingLikedFood = PreferencesUtil.getPreferencesStringSet("myLikeFood").toMutableSet()

                                if (selectItem.isSelected) {
                                    existingLikedFood.add(selectItem.menuImage.toString())
                                } else {
                                    existingLikedFood.remove(selectItem.menuImage.toString())
                                }

                                PreferencesUtil.setPreferencesStringSet("myLikeFood", existingLikedFood)

                                mAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        fun newInstance(): FrgWestFood {
            return FrgWestFood().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}