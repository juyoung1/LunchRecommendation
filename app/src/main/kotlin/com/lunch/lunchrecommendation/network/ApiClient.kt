package com.lunch.lunchrecommendation.network

import com.lunch.lunchrecommendation.network.util.interceptor.DecryptionInterceptor
import com.lunch.lunchrecommendation.network.util.interceptor.HeaderInterceptor
import com.lunch.lunchrecommendation.util.CommonUtils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {

        // Retrofit client
        @JvmStatic
        fun getClient(): Retrofit = retrofit

        private val retrofit: Retrofit by lazy {

            val clientBuilder = CommonUtils.getUnsafeOkHttpClient()
            setOkHttpClient(clientBuilder)

            Retrofit.Builder()
                    .client(clientBuilder.build())
                    .baseUrl(HttpConstants.getHost())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(gsonFactory())
                    .build()
        }

        private fun gsonFactory() : GsonConverterFactory {
            val gson = GsonBuilder().setLenient().create()
            return GsonConverterFactory.create(gson)
        }

        private fun setOkHttpClient(builder: OkHttpClient.Builder){

            builder.addInterceptor(HeaderInterceptor())
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(DecryptionInterceptor())
                .build()
        }
    }
}