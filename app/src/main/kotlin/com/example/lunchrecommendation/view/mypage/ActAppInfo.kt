package com.example.lunchrecommendation.view.mypage

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseContractActivity
import com.example.lunchrecommendation.databinding.ActAppInfoBinding
import com.example.lunchrecommendation.util.CommonUtils

/**
 * 마이 페이지 - 앱 정보
 */
class ActAppInfo : BaseContractActivity<ActAppInfoBinding>() {

    override fun getViewBinding() = ActAppInfoBinding.inflate(layoutInflater)

    override fun initData() {

        slideAnimation = true
        fullScreen = false
        bgStatusBar = R.color.color_f5f5f5
    }

    override fun initView() {
        CommonUtils.log(localClassName)

        initDisplay()
        setClickListener()
    }

    /**
     * Initialize Display
     */
    private fun initDisplay() {

        with(mBinding) {

            incHeader.tvTitle.text = getString(R.string.home_text_5)
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            // 뒤로 가기
            incHeader.ivBack.setOnClickListener { onBackPressed() }

            // 휴대폰 번호
            tvPhoneNumber.setOnClickListener {

                val phoneNumber = tvPhoneNumber.text.toString().trim()

                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }

                if (dialIntent.resolveActivity(packageManager) != null) {
                    startActivity(dialIntent)
                } else {
                    Toast.makeText(this@ActAppInfo, "전화가 지원되지 않습니다", Toast.LENGTH_SHORT).show()
                }
            }

            tvEmail.setOnClickListener {

            }
        }
    }
}