package com.lunch.lunchrecommendation.view.menu.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lunch.lunchrecommendation.base.BaseFragment
import com.lunch.lunchrecommendation.component.GridLayoutItemDecoration
import com.lunch.lunchrecommendation.data.dao.MenuDao
import com.lunch.lunchrecommendation.databinding.FrgMenuListBinding
import com.lunch.lunchrecommendation.util.PreferencesUtil
import com.lunch.lunchrecommendation.view.adapter.MenuListAdapter
import com.lunch.lunchrecommendation.view.home.activity.ActHome
import com.lunch.lunchrecommendation.view.util.MenuListUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 메뉴 탭 - 일식
 */
class FrgJapanFood : BaseFragment<FrgMenuListBinding>() {

    // 메뉴 리스트 어댑터
    private lateinit var mAdapter: MenuListAdapter

    // 메뉴 리스트 데이터
    private val mData: ArrayList<MenuDao> = ArrayList()

    // 내가 찜했던 메뉴
    private val myLikedFood = PreferencesUtil.getPreferencesStringSet("myLikeFood")

    // Shimmer Layout 실행 되었는지 여부
    private var loadingVisible = false

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgMenuListBinding.inflate(inflater, container, false)

    override fun initData() {

        // 메뉴 리스트 데이터
        mData.clear()
        mData.addAll(MenuListUtil.japanFood())

        // 찜 했던 메뉴 찜 되어 있는 상태로 보여짐
        for (menu in mData) {
            menu.isSelected = myLikedFood.contains(menu.menuImage.toString())
        }
    }

    override fun initView() {

        initRecyclerView()
        showLoadingVisible()
    }

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
                    itemAnimator = null
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

                                // 찜 선택/해제 시 찜한 목록에 추가/제거
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

    /**
     * Shimmer Layout
     */
    private fun showLoading() {

        lifecycleScope.launch {
            showShimmerLayout(isLoading = true)
            delay(800)

            mAdapter.notifyDataSetChanged()

            showShimmerLayout(isLoading = false)
        }
    }

    /**
     * Shimmer Layout Visible
     */
    private fun showShimmerLayout(isLoading: Boolean) {

        with(mBinding) {

            if (isLoading) {

                shimmerLayout.startShimmer()
                shimmerLayout.visibility = View.VISIBLE
                rvList.visibility = View.GONE

            } else {

                shimmerLayout.stopShimmer()
                shimmerLayout.visibility = View.GONE
                rvList.visibility = View.VISIBLE
            }
        }
    }

    /**
     *  Shimmer 탭 선택 시 한번만 보여줌
     */
    private fun showLoadingVisible() {

        if (!loadingVisible) { showLoading() }
        loadingVisible = true
    }

    companion object {

        fun newInstance(): FrgJapanFood {
            return FrgJapanFood().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}