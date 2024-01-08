package com.example.lunchrecommendation.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


class NetworkUtil {

    companion object {

        @JvmStatic
        fun isConnectNetwork(context: Context, positive: () -> Unit, negative: () -> Unit): Boolean {

            if (isNetworkAvailable(context)) {

                return true

            } else {
            }

            return false
        }

        @JvmStatic
        fun isNetworkAvailable(context: Context): Boolean {

            val connectivityManager = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

                if (capabilities != null) {

                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true
                    }
                }

            } else {

                val networkInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo ?: null
                if (networkInfo != null) {
                    return networkInfo.isConnected
                }
            }

            return false
        }
    }
}