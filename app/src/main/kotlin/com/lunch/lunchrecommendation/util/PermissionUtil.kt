package com.lunch.lunchrecommendation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

object PermissionUtil {

    /**
     * 블루투스 권한
     */
    fun blePermissions(): Array<String> {

        return if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ) {
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE
            )
        } else {
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        }
    }

    /**
     * 블루투스 권한 체크
     */
    fun checkBlePermissions(context: Context): Boolean {

        var granted = true

        blePermissions().forEach {
            if ( ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED ) {
                granted = false
                return@forEach
            }
        }

        return granted
    }

    /**
     * 다른 앱 위에 표시 권한 체크
     *
     * @param   context     Context
     *
     * @return  다른 앱 위에 표시 권한 여부
     */
    fun checkPermissionOverlay(context: Context): Boolean {

        val granted = Settings.canDrawOverlays(context)
        CommonUtils.log("Permission Overlay granted : $granted")
        return granted
    }

    /**
     * 배터리 사용량 최적화 중지 권한 체크
     */
    fun checkBatteryOptimization(context: Context): Boolean {

        var granted = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager?
            granted = powerManager?.isIgnoringBatteryOptimizations(context.packageName) ?: false
        }
        CommonUtils.log("배터리 사용량 최적화 중지 권한: $granted")
        return granted
    }

    /**
     * 저장소 Manager 권한
     */
    fun checkManageExternalStorage(): Boolean {

        var granted = true

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ) {
            granted = Environment.isExternalStorageManager()
        }

        CommonUtils.log("저장소 Manager 권한 : $granted")
        return granted
    }

    /**
     * 저장소 읽기 권한
     */
    fun checkReadExternalStorage(context: Context): Boolean {

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager() ) {
            return true
        }

        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        CommonUtils.log("저장소 읽기 권한 : $granted")
        return granted
    }

    /**
     * 저장소 쓰기 권한
     */
    fun checkWriteExternalStorage(context: Context): Boolean {

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager() ) {
            return true
        }

        val granted = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        CommonUtils.log("저장소 쓰기 권한 : $granted")
        return granted
    }

    /**
     * 알림 활성화 여부 확인
     *
     * @param   context     Context
     *
     * @return  알림 활성화 여부
     */
    fun areNotificationsEnabled(context: Context): Boolean {

        val enabled = NotificationManagerCompat.from(context).areNotificationsEnabled()
        CommonUtils.log("are Notifications Enabled : $enabled")
        return enabled
    }

    /**
     * 위치 권한 체크
     */
    fun checkLocationPermission(context: Context): Boolean {

        val granted = context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        CommonUtils.log("check Location Permission ---> $granted")
        return granted
    }


    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {

        return permissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
    }

    fun checkLocationPermissions(context: Context): Boolean {

        return context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

    }
}