package com.lunch.lunchrecommendation.view.nickname

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.lunch.lunchrecommendation.R
import com.lunch.lunchrecommendation.base.BaseContractActivity
import com.lunch.lunchrecommendation.databinding.ActNicknameBinding
import com.lunch.lunchrecommendation.extensions.focusAndShowKeyboard
import com.lunch.lunchrecommendation.util.CommonUtils
import com.lunch.lunchrecommendation.util.PreferencesUtil
import com.lunch.lunchrecommendation.view.home.activity.ActHome

/**
 * 닉네임 입력
 */
class ActNickName : BaseContractActivity<ActNicknameBinding>() {

    // 닉네임
    private var nickName = ""

    override fun getViewBinding() = ActNicknameBinding.inflate(layoutInflater)

    override fun initData() {

        fullScreen = false
        bgStatusBar = R.color.color_f5f5f5
    }

    override fun initView() {
        CommonUtils.log(localClassName)

        initDisplay()
        setClickListener()
        initEditText()
    }

    /**
     * Initialize Display
     */
    private fun initDisplay() {

        with(mBinding) {

            // 버튼 비활성화
            btnNext.isEnabled = false

            // 헤더
            incHeader.tvTitle.text = getString(R.string.nick_name_text_1)
            incHeader.ivSetting.visibility = View.GONE
        }
    }

    /**
     * 클릭 리스너 초기화
     */
    private fun setClickListener() {

        with(mBinding) {

            /** 다음 */
            btnNext.setOnClickListener {

                PreferencesUtil.setPreferencesBoolean("nickNameView", true)
                PreferencesUtil.setPreferencesString("nickName", nickName)

                Intent(this@ActNickName, ActHome::class.java).apply {

                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                    finish()
                }
            }
        }
    }

    /**
     * Edit Text 초기화
     */
    private fun initEditText() {

        with(mBinding) {

            // 닉네임 입력
            etNickName.apply {

                addTextChangedListener(object : TextWatcher {

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun afterTextChanged(s: Editable?) {

                        nickName = etNickName.text.toString().trim()

                        if (nickName.length > 8) {
                            etNickName.setText(nickName.substring(0, 8))
                            etNickName.setSelection(8)
                            tvWarning.visibility = View.VISIBLE
                        } else {
                            btnNext.isEnabled = nickName.length in 1..8
                            tvWarning.visibility = View.GONE
                        }
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }
            etNickName.focusAndShowKeyboard(this@ActNickName)
        }
    }
}