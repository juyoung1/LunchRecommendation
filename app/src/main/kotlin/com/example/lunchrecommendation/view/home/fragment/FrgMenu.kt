package com.example.lunchrecommendation.view.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.lunchrecommendation.base.BaseActivity
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.component.ViewPagerAdapter
import com.example.lunchrecommendation.data.dao.MenuDao
import com.example.lunchrecommendation.databinding.FrgMenuBinding
import com.example.lunchrecommendation.view.adapter.MenuTabListAdapter
import com.example.lunchrecommendation.view.menu.fragment.FrgChinaFood
import com.example.lunchrecommendation.view.menu.fragment.FrgFastFood
import com.example.lunchrecommendation.view.menu.fragment.FrgJapanFood
import com.example.lunchrecommendation.view.menu.fragment.FrgKoreaFood
import com.example.lunchrecommendation.view.menu.fragment.FrgNoodle
import com.example.lunchrecommendation.view.menu.fragment.FrgSnackBar
import com.example.lunchrecommendation.view.menu.fragment.FrgWestFood
import dagger.hilt.android.AndroidEntryPoint

/**
 * 홈 - 메뉴
 */
@AndroidEntryPoint
class FrgMenu : BaseFragment<FrgMenuBinding>() {

    // 뷰페이저 관련 변수
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private val fragments: ArrayList<BaseFragment<*>> = ArrayList()

    // 메뉴 탭 리스트 어댑터
    private lateinit var mAdapter: MenuTabListAdapter
    private val menuTabList: ArrayList<MenuDao> = ArrayList()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgMenuBinding.inflate(inflater, container, false)

    override fun initData() {

        for (menu in 0..6) {

            val data = MenuDao()
            val category = when(menu) {

                0 -> {"한식"}
                1 -> {"면"}
                2 -> {"양식"}
                3 -> {"분식"}
                4 -> {"일식"}
                5 -> {"중식"}
                6 -> {"패스트 푸드"}
                else -> {""}
            }
            data.menu = category
            menuTabList.add(data)
        }
    }

    override fun initView() {

        initRecyclerView()
        initViewPager()

        menuTabList[0].isSelected = true
        mBinding.viewPager.currentItem = 0
    }

    /**
     * Initialize RecyclerView
     */
    private fun initRecyclerView() {

        context.let { ctx ->

            with(mBinding) {

                rvList.apply {

                    mAdapter = MenuTabListAdapter(ctx, menuTabList)
                    layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                    adapter = mAdapter
                    mAdapter.selectItem = object : MenuTabListAdapter.SelectItem {
                        override fun selectItem(position: Int) {
                            if (menuTabList.size > position) {

                                mBinding.viewPager.currentItem = position

                                for (i in 0 until menuTabList.size) {
                                    menuTabList[i].isSelected = (i == position)
                                }
                                mAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }
    }

    /**개
     * ViewPager 초기화
     */
    private fun initViewPager() {

        with(mBinding) {

            activity?.let { act ->

                val frgKoreaFood = FrgKoreaFood.newInstance().apply {  }
                val frgNoodle = FrgNoodle.newInstance().apply {  }
                val frgWestFood = FrgWestFood.newInstance().apply {  }
                val frgSnackBar = FrgSnackBar.newInstance().apply {  }
                val frgJapanFood = FrgJapanFood.newInstance().apply {  }
                val frgChinaFood = FrgChinaFood.newInstance().apply {  }
                val frgFastFood = FrgFastFood.newInstance().apply {  }

                fragments.add(frgKoreaFood)
                fragments.add(frgNoodle)
                fragments.add(frgWestFood)
                fragments.add(frgSnackBar)
                fragments.add(frgJapanFood)
                fragments.add(frgChinaFood)
                fragments.add(frgFastFood)

                viewPagerAdapter = ViewPagerAdapter(act as BaseActivity<*>, fragments)
                viewPager.adapter = viewPagerAdapter
                viewPager.isUserInputEnabled = false
                viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        fragments[position].reLoad()
                    }
                })
            }
        }
    }

    companion object {

        fun newInstance(): FrgMenu {
            return FrgMenu().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}