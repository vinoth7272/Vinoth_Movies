package com.example.movierating.data.network

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    fun getNetworkError(code: Int, message: String): String {
        return when {
            code == 504 -> {
                "Timeout Server Not Respond"
            }
            message.isNotEmpty() -> {
                message
            }
            else -> {
                "Something went wrong."
            }
        }
    }

    /**
     * Check weather the internet is available or not
     * @return true if available else false
     */
    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
