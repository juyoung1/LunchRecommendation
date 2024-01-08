package com.example.lunchrecommendation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding



abstract class BaseFragment<B: ViewBinding> : Fragment(){

    var _mBinding: B? = null
    protected val mBinding get() = _mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _mBinding = getViewBinding(inflater, container)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mBinding = null
    }

    abstract fun initData()
    abstract fun initView()
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B
    open fun reLoad() {}

    /**
     * ViewModel Factory Lazy
     */
    inline fun <reified viewModel : ViewModel> createViewModel(factory: ViewModelProvider.Factory? = null): viewModel {
        return if (factory == null) {
            ViewModelProvider(requireActivity())[viewModel::class.java]
        } else {
            ViewModelProvider(requireActivity(), factory)[viewModel::class.java]
        }
    }
}