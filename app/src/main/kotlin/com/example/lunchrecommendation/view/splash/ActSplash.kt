package com.example.lunchrecommendation.view.splash

import android.content.Intent
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseContractActivity
import com.example.lunchrecommendation.databinding.ActSplashBinding
import com.example.lunchrecommendation.util.CommonUtils
import com.example.lunchrecommendation.util.DataShareUtil
import com.example.lunchrecommendation.util.MethodStorage
import com.example.lunchrecommendation.util.PreferencesUtil
import com.example.lunchrecommendation.view.home.activity.ActHome
import com.example.lunchrecommendation.view.nickname.ActNickName

/**
 * 스플래시
 */
class ActSplash : BaseContractActivity<ActSplashBinding>() {

    override fun getViewBinding() = ActSplashBinding.inflate(layoutInflater)

    override fun initData() {

        fullScreen = true

        // 너비 및 높이
        DataShareUtil.screenWidth = MethodStorage.getScreenWidth(this)
        DataShareUtil.screenHeight = MethodStorage.getScreenHeight(this)
    }

    override fun initView() {
        CommonUtils.log(localClassName)

        customizeText(mBinding.tvTitle)
        move()
    }

    /**
     * 화면 이동
     */
    private fun move() {

        // 화면 이동
        Handler(mainLooper).postDelayed({

            if (PreferencesUtil.getPreferencesBoolean("nickNameView")) {

                Intent(this@ActSplash, ActHome::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                    finish()
                }
            } else {

                Intent(this@ActSplash, ActNickName::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                    finish()
                }
            }
        }, 1000)
    }

    /**
     * 텍스트 Spannable
     */
    private fun customizeText(spanText: TextView) {

        val titleText = getString(R.string.app_title)
        val indices = listOf(0, 3, 6)
        val textSize = 45
        val textColor = ContextCompat.getColor(this, R.color.black)

        val spannableString = SpannableString(titleText)

        for (index in indices) {

            // 폰트 변경
            spannableString.setSpan(StyleSpan(R.font.samsung_sharpsans_bold), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 텍스트 크기 변경
            spannableString.setSpan(AbsoluteSizeSpan(textSize, true), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 텍스트 색상 변경
            spannableString.setSpan(ForegroundColorSpan(textColor), index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        spanText.text = spannableString
    }
}