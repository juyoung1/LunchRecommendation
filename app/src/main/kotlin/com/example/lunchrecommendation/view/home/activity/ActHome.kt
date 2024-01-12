package com.example.lunchrecommendation.view.home.activity

import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseContractActivity
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.ActHomeBinding
import com.example.lunchrecommendation.util.CommonUtils
import com.example.lunchrecommendation.constants.Enum
import com.example.lunchrecommendation.util.PreferencesUtil
import com.example.lunchrecommendation.view.home.fragment.FrgMenu
import com.example.lunchrecommendation.view.home.fragment.FrgMyPage

/**
 * 홈
 */
class ActHome : BaseContractActivity<ActHomeBinding>() {

    /** Current fragment */
    private var currentFragment: BaseFragment<*>? = null

    override fun getViewBinding() = ActHomeBinding.inflate(layoutInflater)

    override fun initData() {

        bgStatusBar = R.color.color_f5f5f5
        fullScreen = false
    }

    override fun initView() {
        CommonUtils.log(localClassName)

        initDisplay()
        setClickListener()

        // 첫 화면 세팅
        changeMainFragment(Enum.Page.Menu)
    }

    /**
     * Display 초기화
     */
    private fun initDisplay() {

        with( mBinding ) {

            // 메뉴
            incNaviMenu.apply {

                tvNavi.text = getString(R.string.menu)
                ivNavi.setImageResource(R.drawable.ic_menu)
            }

            // 마이 페이지
            incNaviMyPage.apply {

                tvNavi.text = getString(R.string.myPage)
                ivNavi.setImageResource(R.drawable.ic_my_page)
            }
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with( mBinding ) {

            /** 메뉴 탭 */
            incNaviMenu.btnNavi.setOnClickListener { changeMainFragment(Enum.Page.Menu) }

            /** 마이 페이지 탭 */
            incNaviMyPage.btnNavi.setOnClickListener { changeMainFragment(Enum.Page.MyPage) }
        }
    }

    /**
     * 화면 전환
     */
    private fun changeMainFragment(page: Enum.Page) {

        val fragment = when( page ) {

            Enum.Page.Menu -> { FrgMenu.newInstance() }
            Enum.Page.MyPage -> { FrgMyPage.newInstance() }
        }

        currentFragment = fragment
        replaceFragment(R.id.flHome, fragment)

        // 헤더 변경
        changeHeader(page)

        // Bottom navigation button 변경
        mBinding.incNaviMenu.btnNavi.isSelected = page == Enum.Page.Menu
        mBinding.incNaviMyPage.btnNavi.isSelected = page == Enum.Page.MyPage

        selectedTextStyle(mBinding.incNaviMenu.btnNavi.isSelected, mBinding.incNaviMenu.tvNavi)
        selectedTextStyle(mBinding.incNaviMyPage.btnNavi.isSelected, mBinding.incNaviMyPage.tvNavi)
    }

    /**
     * 네비 아이템 선택 시 텍스트 스타일 변경
     */
    private fun selectedTextStyle(selected: Boolean, textView: TextView) {
        val fontStyle = if (selected) R.font.samsung_sharpsans_bold else R.font.samsung_one_korean_500
        val textSize = if (selected) 14f else 12f

        textView.typeface = ResourcesCompat.getFont(this@ActHome, fontStyle)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize)
    }

    /**
     * 헤더 텍스트 변경
     */
    private fun changeHeader(page: Enum.Page) {

        with( mBinding ) {

            // 헤더 텍스트
            incHeader.tvTitle.text = when( page ) {

                Enum.Page.Menu -> { getString(R.string.home_text_1, PreferencesUtil.getPreferencesString("nickName")) }
                Enum.Page.MyPage -> { getString(R.string.myPage) }
            }
        }
    }
}