package com.example.lunchrecommendation.util

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.example.lunchrecommendation.R

object ToastUtil {

    private var toast: Toast? = null

    /**
     * 찜 완료 토스트
     */
    fun showSelected(context: Context?, message: String?) {

        if ( context == null ) {
            return
        }

        if ( message.isNullOrEmpty() ) {
            return
        }

        toast?.cancel()
        toast = createToast(context, message, Color.BLACK, R.drawable.ic_heart_sel, 150)
        toast?.show()
    }

    /**
     * 찜 해제 토스트
     */
    fun showUnSelected(context: Context?, message: String?) {

        if ( context == null ) {
            return
        }

        if ( message.isNullOrEmpty() ) {
            return
        }

        toast?.cancel()
        toast = createToast(context, message, Color.BLACK, R.drawable.ic_heart_white, 150)
        toast?.show()
    }

    private fun createToast(context: Context, message: String, textColor: Int, @DrawableRes icon: Int, position: Int): Toast {
        return Toast(context).apply {
            setGravity(Gravity.BOTTOM, 0, position)

            view = LayoutInflater.from(context).inflate(R.layout.toast_common, null, false).apply {

                view = findViewById<TextView>(R.id.tvMessage).apply {

                    text = message
                    setTextColor(textColor)
                }

                view = findViewById<AppCompatImageView>(R.id.ivIcon).apply {

                    setImageResource(icon)
                }
            }
        }
    }
}