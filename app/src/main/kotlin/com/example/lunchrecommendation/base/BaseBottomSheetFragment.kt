package com.example.lunchrecommendation.base

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding
import com.example.lunchrecommendation.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetFragment<B: ViewBinding> : BottomSheetDialogFragment() {

    private var _mBinding: B? = null
    protected val mBinding get() = _mBinding!!

    var isFullScreen = false

    interface SheetDismissListener { fun dismiss() }
    var sheetDismissListener: SheetDismissListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _mBinding = getViewBinding(inflater, container)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _mBinding = null
    }

    /**
     * sheet 풀스크린
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val bottomSheetDialog = BottomSheetDialog(requireContext(), theme)

        if (isFullScreen) {

            bottomSheetDialog.setOnShowListener {

                val bottomSheet = (it as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

                BottomSheetBehavior.from((bottomSheet as FrameLayout)).state = BottomSheetBehavior.STATE_EXPANDED
                BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
                BottomSheetBehavior.from(bottomSheet).isHideable = true

                val layoutParams = bottomSheet.layoutParams

                layoutParams.height = getWindowHeight()
                bottomSheet.layoutParams = layoutParams
            }
        }

        return bottomSheetDialog
    }

    private fun getWindowHeight(): Int {

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

        return displayMetrics.heightPixels
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        sheetDismissListener?.let { it.dismiss() }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getTheme(): Int  = R.style.Theme_NoWiredStrapInNavigationBar

    abstract fun initData()
    abstract fun initView()
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B
    open fun reLoad() {}
}