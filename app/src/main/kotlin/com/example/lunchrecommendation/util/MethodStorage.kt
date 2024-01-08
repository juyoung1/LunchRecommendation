package com.example.lunchrecommendation.util

import android.app.Activity
import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.ContextCompat

class MethodStorage {

    companion object {

        private var context: Context? = null

        /**
         * getScreenHeight
         */
        fun getScreenHeight(activity: Activity): Int {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                val windowMetrics = activity.windowManager.currentWindowMetrics
                val insets: Insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(
                    WindowInsets.Type.systemBars()
                )
                windowMetrics.bounds.height() - insets.top - insets.bottom

            } else {

                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.heightPixels
            }
        }

        /**
         * getScreenWidth
         */
        fun getScreenWidth(activity: Activity): Int {

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

                val windowMetrics = activity.windowManager.currentWindowMetrics
                val insets =
                    windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                windowMetrics.bounds.width() - insets.left - insets.right

            } else {

                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
        }

        /**
         * 기본 기기명 가져오기
         * @return ex) Galaxy Z Flip4
         */
        fun defaultDeviceName(context: Context): String {

            return try {
                Settings.Global.getString(context.contentResolver, "default_device_name")
            } catch (e: Exception) {
                ""
            }

        }

        /**
         * Status Bar 색상 변경
         */
        fun setStatusBarColor(activity: Activity?, color: Int) {

            activity?.let {

                val window: Window = it.window

                // clear FLAG_TRANSLUCENT_STATUS flag:
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

                // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

                // finally change the color
                window.statusBarColor = ContextCompat.getColor(it, color)
            }
        }
    }
}