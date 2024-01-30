package com.lunch.lunchrecommendation.network

import com.lunch.lunchrecommendation.BuildConfig


class HttpConstants {

    companion object {

        // Server define
        private const val SERVICE = 0
        private const val DEV = 1

        // Current
        //--------------------------------------------------
        private val SERVER: Int = DEV

        /** API 관리 서버 */
        @JvmStatic
        fun getHost(): String = when (BuildConfig.DEBUG) {

            true -> when(SERVER) {
                SERVICE -> SERVICE_SERVER_HOST
                DEV -> DEV_SERVER_HOST
                else -> DEV_SERVER_HOST
            }
            false -> SERVICE_SERVER_HOST
        }

        // Server Release Url
        //--------------------------------------------------
        private const val SERVICE_SERVER_HOST = ""
        private const val DEV_SERVER_HOST = ""

        // Api url
        // const val URL = "/"          //  api

    }
}