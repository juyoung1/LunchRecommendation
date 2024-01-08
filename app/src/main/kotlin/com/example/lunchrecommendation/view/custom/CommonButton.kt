package com.example.lunchrecommendation.view.custom

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import com.example.lunchrecommendation.R

@SuppressLint("UseCompatLoadingForDrawables")
class CommonButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {

        gravity = Gravity.CENTER
        setTextColor(context.getColor(R.color.text_common_btn))
        background = context.getDrawable(R.drawable.bg_common_btn)
    }
}