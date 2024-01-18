package com.example.lunchrecommendation.view.home.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseFragment
import com.example.lunchrecommendation.databinding.FrgMenuRouletteBinding
import com.example.lunchrecommendation.view.custom.RotateListener
import com.example.lunchrecommendation.view.util.MenuListUtil

/**
 * 홈 - 룰렛
 */
class FrgMenuRoulette : BaseFragment<FrgMenuRouletteBinding>() {

    /** 룰렛 돌리는 중 스트링 애니메이션 */
    private var dotAnimator = ValueAnimator.ofInt(0, 5)

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FrgMenuRouletteBinding.inflate(inflater, container, false)

    override fun initData() {}

    override fun initView() {

        initDisplay()
        setClickListener()
    }

    /**
     * Initialize Display
     */
    private fun initDisplay() {

        rouletteMenuSetting()
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            /** 룰렛 돌리기 */
            btnRoulette.setOnClickListener { rotateRoulette() }

            /** 메뉴 새로고침 */
            tvReset.setOnClickListener {

                rouletteMenuSetting()
                tvTodayMenu.text = getString(R.string.home_text_20)
            }
        }
    }

    /**
     * 룰렛 메뉴 세팅
     */
    private fun rouletteMenuSetting() {

        with(mBinding) {

            // MenuUtil 에서 모든 메뉴 중 무작위로 6개 메뉴 가져옴
            val randomMenus = MenuListUtil.getAllMenus().shuffled().take(6)

            // 메뉴 데이터 중 메뉴 이름 가져옴
            val rouletteDataList = randomMenus.map { menuDao ->
                menuDao.menu.toString()
            }

            roulette.rouletteSize = 6
            roulette.setRouletteDataList(rouletteDataList)
        }
    }

    /**
     * 룰렛 회전
     */
    private fun rotateRoulette() {

        with(mBinding) {

            val rouletteListener = object : RotateListener {

                /** 룰렛 회전 시작 */
                override fun onRotateStart() {

                    startDotAnimation()
                    btnRoulette.isEnabled = false
                }

                /** 룰렛 회전 종료 */
                override fun onRotateEnd(result: String) {

                    stopDotAnimation()
                    btnRoulette.isEnabled = true

                    // 결과 텍스트 설정 및 폰트, 사이즈 적용
                    val resultText = getString(R.string.home_text_18, result)
                    val spannableString = SpannableString(resultText)
                    spannableString.setSpan(AbsoluteSizeSpan(24, true), resultText.indexOf(result), resultText.indexOf(result) + result.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannableString.setSpan(StyleSpan(R.font.samsung_sharpsans_bold), resultText.indexOf(result), resultText.indexOf(result) + result.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    tvTodayMenu.text = spannableString
                }
            }

            val toDegrees = (2000..4000).random().toFloat()
            roulette.rotateRoulette(toDegrees, 2000, rouletteListener)
        }
    }

    /**
     * 룰렛 돌리는 중 스트링 애니메이션
     */
    private fun startDotAnimation() {

        dotAnimator.duration = 1200
        dotAnimator.interpolator = LinearInterpolator()
        dotAnimator.repeatCount = ValueAnimator.INFINITE
        dotAnimator.repeatMode = ValueAnimator.REVERSE

        dotAnimator.addUpdateListener { valueAnimator ->
            val dotsCount = valueAnimator.animatedValue as Int
            val dots = StringBuilder("오늘의 점심 메뉴는")
            repeat(dotsCount) {
                dots.append('.')
            }
            mBinding.tvTodayMenu.text = dots.toString()
        }

        dotAnimator.start()
    }

    /**
     * 룰렛 종료 시 스트링 애니메이션 종료
     */
    private fun stopDotAnimation() { dotAnimator.cancel() }

    companion object {

        fun newInstance(): FrgMenuRoulette {
            return FrgMenuRoulette().apply {
                arguments = Bundle().apply {}
            }
        }
    }
}