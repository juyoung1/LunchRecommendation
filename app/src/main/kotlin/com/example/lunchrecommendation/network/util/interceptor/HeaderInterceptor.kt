package com.example.lunchrecommendation.network.util.interceptor


import com.example.lunchrecommendation.util.CommonUtils
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val newBuilder = original.newBuilder()

        val request = newBuilder.build()

        CommonUtils.log("Request URL : " + original.url)
        CommonUtils.log("Request Body : " + toString(original.body))

        return chain.proceed(request)
    }

    fun toString(request: RequestBody?): String? {
        return try {
            val buffer = Buffer()
            if (request != null) {
                request.writeTo(buffer)
            } else {
                return ""
            }
            buffer.readUtf8()
        } catch (e: IOException) {
            ""
        }
    }
}