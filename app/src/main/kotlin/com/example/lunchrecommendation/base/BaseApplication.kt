package com.example.lunchrecommendation.base

import android.app.Application
import android.os.Process
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.lunchrecommendation.util.CommonUtils
import com.example.lunchrecommendation.util.PreferencesUtil
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlin.system.exitProcess


@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private var exceptionHandler: Thread.UncaughtExceptionHandler? = null

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        initializeService()
    }

    /**
     * 서비스 설정
     */
    private fun initializeService() {

        applicationContext?.let {

            /**
             * SharedPreferences 유틸 초기화
             */
            PreferencesUtil.context = it

            setDefaultUncaughtExceptionHandler()
        }
    }

    private fun setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler)
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        CommonUtils.log("onCreate() >> exceptionHandler: $exceptionHandler")
    }

    private val uncaughtExceptionHandler =
        Thread.UncaughtExceptionHandler { thread, ex ->
            CommonUtils.log("uncaughtException() >> Start !!!")
            CommonUtils.log("uncaughtException() >> thread: $thread")
            try {
                CommonUtils.log("uncaughtException() >> Throwable: $ex")
                Process.killProcess(Process.myPid())
                exitProcess(10)
            } catch (e: Exception) {
                CommonUtils.log("uncaughtException() >> Exception: " + e.localizedMessage)
            }
        }

    companion object {
        lateinit var app: Application
    }
}