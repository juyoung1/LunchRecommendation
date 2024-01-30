package com.lunch.lunchrecommendation.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.lunch.lunchrecommendation.R
import com.lunch.lunchrecommendation.databinding.PopupCommonBinding

class PopupCommon(context: Context) : Dialog(context, R.style.RoundCornerDialog) {

    lateinit var mBinding: PopupCommonBinding

    private var title: String? = null
    private var message: String? = null
    private var leftBtnName: String? = null
    private var rightBtnName: String? = null
    private var leftClickListener: View.OnClickListener? = null
    private var rightClickListener: View.OnClickListener? = null
    var mCancelable = false

    /**
     * 기본
     */
    constructor(context: Context, title: String?, message: String?) : this(context) {

        this.title = title
        this.message = message
    }

    /**
     * 오른쪽 버튼
     */
    constructor(context: Context, title: String?, message: String?, btnName: String?, clickListener: View.OnClickListener?) : this(context, title, message) {

        this.rightBtnName = btnName
        this.rightClickListener = clickListener
    }

    /**
     * 오른쪽, 왼쪽 버튼
     */
    constructor(context: Context, title: String?, message: String?, leftBtnName: String?, rightBtnName: String?, leftClickListener: View.OnClickListener?, rightClickListener: View.OnClickListener?)
            : this(context, title, message) {

        this.leftBtnName = leftBtnName
        this.leftClickListener = leftClickListener
        this.rightBtnName = rightBtnName
        this.rightClickListener = rightClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCancelable(mCancelable)

        mBinding = PopupCommonBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        with(mBinding) {

            // 타이틀
            if (title != null) {

                tvTitle.text = title
                tvTitle.visibility = View.VISIBLE

            } else {
                tvTitle.visibility = View.GONE
            }

            // 내용
            if (message != null) {

                tvContent.text = message
                tvContent.visibility = View.VISIBLE

            } else {
                tvContent.visibility = View.GONE
            }

            // left btn
            if (leftClickListener != null && leftBtnName != null) {

                btnLeft.setOnClickListener {
                    leftClickListener?.onClick(btnLeft)
                    dismiss()
                }
                leftBtnName?.let { btnLeft.text = leftBtnName }

                btnLeft.visibility = View.VISIBLE
                vwDivider.visibility = View.VISIBLE

            } else {

                btnLeft.visibility = View.GONE
                vwDivider.visibility = View.GONE
            }

            // right btn
            btnRight.setOnClickListener {

                if (rightClickListener == null) {
                    dismiss()
                } else {
                    rightClickListener?.onClick(btnRight)
                    dismiss()
                }
            }

            rightBtnName?.let {
                btnRight.text = it
            }
        }
    }
}