package com.lunch.lunchrecommendation.network

import com.google.gson.annotations.SerializedName


data class BaseResponse<T> (

        // 결과값
        @JvmField
        @SerializedName("result")
        var result: Boolean = false,

        // 상태코드 (200:정상, 422:오류, 500:시스템오류)
        @JvmField
        @SerializedName("status")
        var status: Int? = null,

        // 데이터
        @JvmField
        @SerializedName("data")
        var data: T? = null,

        // 오류메시지
        @JvmField
        @SerializedName("message")
        var message: String? = null,

        // 서버 오류코드
        @JvmField
        @SerializedName("code")
        var code: String? = null
)