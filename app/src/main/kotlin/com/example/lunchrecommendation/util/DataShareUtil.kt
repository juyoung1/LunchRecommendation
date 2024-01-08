package com.example.lunchrecommendation.util

class DataShareUtil {

    companion object {

        var screenWidth = 0              // 스크린 너비
        var screenHeight = 0             // 스크린 높이

        @Volatile private var instance: DataShareUtil? = null
        @JvmStatic fun getInstance(): DataShareUtil =
            instance ?: synchronized(this) {
                instance ?: DataShareUtil().also {
                    instance = it
                }
            }
    }
}