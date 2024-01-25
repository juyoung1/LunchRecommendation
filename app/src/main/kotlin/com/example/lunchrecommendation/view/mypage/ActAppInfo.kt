package com.example.lunchrecommendation.view.mypage

import android.content.Intent
import android.net.Uri
import com.example.lunchrecommendation.BuildConfig
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

            // 헤더
            incHeader.tvTitle.text = getString(R.string.home_text_5)

            // 버전 정보
            tvVersion.text = getString(R.string.home_text_13, BuildConfig.VERSION_NAME)

            // 버전 상태
            tvVersionStatue.isEnabled = false
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            /** 뒤로 가기 */
            incHeader.ivBack.setOnClickListener { onBackPressed() }

            /** 휴대폰 번호 전화 */
            /*tvPhoneNumber.setOnClickListener {
                val phoneNumber = tvPhoneNumber.text.toString().trim()

                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }

                if (ContextCompat.checkSelfPermission(this@ActAppInfo, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                    // 권한이 있으면 전화 걸기
                    startActivity(dialIntent)
                } else {

                    // 권한이 없으면 권한 요청
                    ActivityCompat.requestPermissions(this@ActAppInfo, arrayOf(android.Manifest.permission.CALL_PHONE), 1001)
                }
            }*/

            /** 이메일 보내기 */
            tvEmail.setOnClickListener {

                val emailAddress = getString(R.string.home_text_14)

                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailAddress, null))
                startActivity(Intent.createChooser(emailIntent, ""))
            }
        }
    }
}