package com.lunch.lunchrecommendation.network.util.interceptor


import com.lunch.lunchrecommendation.util.CommonUtils
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody


class DecryptionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        CommonUtils.log(" HTTP RESPONSE CODE : [${response.code}]")
        if (response.isSuccessful) {

            val str = response.body!!.string()
            CommonUtils.log("RESULT: $str")

            val newResponse = response.newBuilder()
            val contentType = "application/json;charset=UTF-8"
            val decrypted = str
            newResponse.body(decrypted.toResponseBody(contentType.toMediaTypeOrNull()))
            return newResponse.build()
        }

        return response
    }
}