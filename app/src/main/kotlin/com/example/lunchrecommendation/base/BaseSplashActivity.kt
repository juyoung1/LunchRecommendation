package com.example.lunchrecommendation.base

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.lunchrecommendation.R
import io.reactivex.rxjava3.disposables.CompositeDisposable

@SuppressLint("CustomSplashScreen")
abstract class BaseSplashActivity<B: ViewBinding> : AppCompatActivity() {

    private var _mBinding: B? = null
    val mBinding get() = _mBinding!!
    protected lateinit var mDisposable: CompositeDisposable

    var fullScreen = true

    var slideAnimation = false
    var verticalSlideAnimation = false

    var bgStatusBar: Int? = null
    var lightStatusBar = true
    var lightNavigationBar = true

    var backPressedCallback: OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        initData()

        // Status bar and navigation bar setting
        initStatusBarAndNaviBar()

        _mBinding = getViewBinding()
        setContentView(mBinding.root)
        initView()

        // Slide 애니메이션 여부
        activityOpenAnimation()

        // 뒤로가기 콜백
        backPressedCallback?.let { onBackPressedDispatcher.addCallback(this, it) }
    }

    override fun setContentView(layoutResID: Int) {
        layoutInflater.inflate(layoutResID, null).let {
            super.setContentView(it)
        }
    }

    abstract fun initData()
    abstract fun initView()
    abstract fun getViewBinding(): B

    /**
     * ViewModel Factory Lazy
     */
    inline fun <reified viewModel : ViewModel> AppCompatActivity.createViewModel(factory: ViewModelProvider.Factory? = null): viewModel {
        return if (factory == null) {
            ViewModelProvider(this)[viewModel::class.java]
        } else {
            ViewModelProvider(this, factory)[viewModel::class.java]
        }
    }

    /**
     * fullscreen
     */
    private fun setFullScreen() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            /** 디스플레이 컷아웃 */
            val lp = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp

            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        } else {

            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    /**
     * Activity Open Animation
     */
    private fun activityOpenAnimation() {

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE ) {

            if ( slideAnimation ) {
                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.anim_window_in, R.anim.anim_window_out)
            } else if (verticalSlideAnimation) {
                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.anim_window_vertical_in, R.anim.anim_window_vertical_out)
            } else {
                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
            }

        } else {

            if ( slideAnimation ) {
                overridePendingTransition(R.anim.anim_window_in, R.anim.anim_window_out)
            } else if (verticalSlideAnimation) {
                overridePendingTransition(R.anim.anim_window_vertical_in, R.anim.anim_window_vertical_out)
            } else {
                overridePendingTransition(0, 0)
            }
        }
    }

    /**
     * Activity Close Animation
     */
    private fun activityCloseAnimation() {

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE ) {

            if ( slideAnimation ) {
                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, R.anim.anim_window_close_in, R.anim.anim_window_close_out)
            } else if (verticalSlideAnimation) {
                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, R.anim.anim_window_vertical_close_in, R.anim.anim_window_vertical_close_out)
            } else {
                overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE, 0, 0)
            }

        } else {

            if ( slideAnimation ) {
                overridePendingTransition(R.anim.anim_window_close_in, R.anim.anim_window_close_out)
            } else if (verticalSlideAnimation) {
                overridePendingTransition(R.anim.anim_window_vertical_close_in, R.anim.anim_window_vertical_close_out)
            } else {
                overridePendingTransition(0, 0)
            }
        }
    }

    /**
     * Status bar and navigation bar setting
     */
    private fun initStatusBarAndNaviBar() {

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // Status 바 배경색 설정
        bgStatusBar?.let {

            window.statusBarColor = ContextCompat.getColor(this@BaseSplashActivity, it)

        } ?: let {

            val color = if ( lightStatusBar ) Color.WHITE else Color.BLACK
            if ( window.statusBarColor != color ) window.statusBarColor = color
        }

        // Status bar 텍스트 색상 설정
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = lightStatusBar

        // 네비게이션 바 아이콘 색상 설정
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = !lightNavigationBar
    }
}