package com.example.lunchrecommendation.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.base.BaseBottomSheetFragment
import com.example.lunchrecommendation.databinding.SheetProfileEditBinding
import com.example.lunchrecommendation.util.PreferencesUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * 프로필 수정 바텀시트
 */
@AndroidEntryPoint
class SheetProfileEdit(val function: (type: String) -> Unit) : BaseBottomSheetFragment<SheetProfileEditBinding>() {

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = SheetProfileEditBinding.inflate(inflater, container, false)

    override fun initData() {

    }

    override fun initView() {

        initDisplay()
        setClickListener()
        initEditText()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomModalDialogTheme)

        bottomSheetDialog.setOnShowListener {

            val bottomSheet =
                (it as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            BottomSheetBehavior.from((bottomSheet as FrameLayout)).state =
                BottomSheetBehavior.STATE_EXPANDED
            BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
            BottomSheetBehavior.from(bottomSheet).isHideable = true
            BottomSheetBehavior.from(bottomSheet).isDraggable = false
        }

        return bottomSheetDialog
    }

    private fun initDisplay() {

        with(mBinding) {

            etNickName.setText(PreferencesUtil.getPreferencesString("nickName"))
            etFavoriteMenu.hint = getString(R.string.sheet_text_9, PreferencesUtil.getPreferencesString("nickName"))
        }
    }

    private fun setClickListener() {

        with(mBinding) {

            tvClose.setOnClickListener { dismiss() }
        }
    }

    private fun initEditText() {

        with(mBinding) {


        }
    }
}