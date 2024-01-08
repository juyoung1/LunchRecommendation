package com.example.lunchrecommendation.base

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.example.lunchrecommendation.R
import com.example.lunchrecommendation.util.CommonUtils
import com.example.lunchrecommendation.util.MethodStorage
import io.reactivex.disposables.CompositeDisposable


abstract class BaseActivity<B: ViewBinding> : AppCompatActivity() {

    private var _mBinding: B? = null
    protected val mBinding get() = _mBinding!!
    protected lateinit var mDisposable: CompositeDisposable

    var slideAnimation = false
    var verticalSlideAnimation = false

    var fullScreen = true

    var bgStatusBar: Int? = null
    var lightStatusBar = true
    var lightNavigationBar = true

    var backPressedCallback: OnBackPressedCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CommonUtils.log("onCreate $localClassName")

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

    override fun finish() {
        super.finish()

        activityCloseAnimation()
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
     * Status bar and navigation bar setting
     */
    private fun initStatusBarAndNaviBar() {

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // Status 바 배경색 설정
        bgStatusBar?.let {

            window.statusBarColor = ContextCompat.getColor(this@BaseActivity, it)

        } ?: let {

            val color = if ( lightStatusBar ) Color.WHITE else Color.BLACK
            window.statusBarColor = color
        }

        // Status bar 텍스트 색상 설정
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = lightStatusBar

        // 네비게이션 바 아이콘 색상 설정
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = lightNavigationBar
    }

    /**
     * Hide KeyBoard
     */
    fun hideKeyboard(view: View?) {

        view?.let {

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * Change fragment
     *
     * @param   containerId     Target fragment container view
     * @param   fragment        Fragment
     */
    fun replaceFragment(containerId: Int, fragment: BaseFragment<*>) {

        if ( !supportFragmentManager.isDestroyed ) {

            supportFragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commit()
        }
    }

    fun isServiceRunning(serviceName: String): Boolean {
        var serviceRunning = false
        val am = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val l = am.getRunningServices(50)
        val i: Iterator<ActivityManager.RunningServiceInfo> = l.iterator()
        while (i.hasNext()) {
            val runningServiceInfo = i
                .next()
            if (runningServiceInfo.service.className == serviceName) {
                serviceRunning = true
                if (runningServiceInfo.foreground) {
                    //service run in foreground
                }
            }
        }
        return serviceRunning
    }
}