package com.example.lunchrecommendation.view.mypage.activity

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseContractActivity
import com.example.lunchrecommendation.component.GridLayoutItemDecoration
import com.example.lunchrecommendation.data.dao.MenuDao
import com.example.lunchrecommendation.databinding.ActMyLikeFoodListBinding
import com.example.lunchrecommendation.util.CommonUtils
import com.example.lunchrecommendation.util.PreferencesUtil
import com.example.lunchrecommendation.view.adapter.MyFavoriteFoodListAdapter
import com.example.lunchrecommendation.view.home.activity.ActHome

/**
 * 마이 페이지 - 내 찜 목록
 */
class ActMyLikeFoodList : BaseContractActivity<ActMyLikeFoodListBinding>() {

    // 내 찜 목록 리스트 어댑터
    private lateinit var mAdapter: MyFavoriteFoodListAdapter
    private val mData: ArrayList<MenuDao> = ArrayList()

    // 찜 한 메뉴
    private val myLikedFood = PreferencesUtil.getPreferencesStringSet("myLikeFood")

    override fun getViewBinding() = ActMyLikeFoodListBinding.inflate(layoutInflater)

    override fun initData() {

        slideAnimation = true
        fullScreen = false
        bgStatusBar = R.color.color_f5f5f5

        // 내 찜 목록 이미지 데이터
        mData.clear()
        for (menuImage in myLikedFood) {

            val data = MenuDao()
            data.menuImage = menuImage
            mData.add(data)
        }

        // 큰 이미지 보일 때 뒤로 가기
        backPressedCallBack()
    }

    override fun initView() {
        CommonUtils.log(localClassName)

        initDisplay()
        setClickListener()
        initRecyclerView()
    }

    /**
     * Initialize Display
     */
    private fun initDisplay() {

        with(mBinding) {

            // 헤더
            incHeader.tvTitle.text = getString(R.string.home_text_3)

            // 찜한 이미지 없을 때 텍스트
            if (myLikedFood.isEmpty()) {

                clNoLikeFood.visibility = View.VISIBLE
                rvList.visibility = View.GONE
            }
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            /** 뒤로 가기 */
            incHeader.ivBack.setOnClickListener { onBackPressed() }

            /** 찜 하러 가기 */
            tvGoLikeFood.setOnClickListener {

                Intent(this@ActMyLikeFoodList, ActHome::class.java).apply {

                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(this)
                    finishAffinity()
                }
            }

            /** 사진 크게 보기 숨기기 */
            ivClose.setOnClickListener {

                // 사진 크게 보기 숨기기
                clBigImage.visibility = View.GONE
            }
        }
    }

    /**
     * RecyclerView 초기화
     */
    private fun initRecyclerView() {

        with(mBinding) {

            /** 내 찜 목록 리스트 */
            rvList.apply {

                val spanCount = 3
                val itemGap = 3

                mAdapter = MyFavoriteFoodListAdapter(this@ActMyLikeFoodList, mData)
                layoutManager = GridLayoutManager(this@ActMyLikeFoodList, spanCount)
                adapter = mAdapter
                addItemDecoration(GridLayoutItemDecoration(context, itemGap, itemGap, spanCount))
                mAdapter.selectItem = object : MyFavoriteFoodListAdapter.SelectItem {

                    override fun selectItem(position: Int, type: String) {

                        if (mData.size > position) {

                            val selectItem = mData[position]

                            when(type) {

                                "click" -> {

                                    clBigImage.visibility = View.VISIBLE
                                    Glide.with(this@ActMyLikeFoodList).load(selectItem.menuImage).into(ivBigImage)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 큰 이미지 보일 때 뒤로 가기
     */
    private fun backPressedCallBack() {

        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if ( mBinding.clBigImage.visibility == View.VISIBLE ) {

                    mBinding.clBigImage.visibility = View.GONE
                    return
                }

                finish()
            }
        }
    }
}