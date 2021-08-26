package com.example.movierating.data.network

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    const val UNAUTHORIZED = "Unauthorized"
    const val NOT_FOUND = "Not found"
    const val SOMETHING_WRONG = "Something went wrong"


    fun getErrorMessage(httpCode: Int): String {
        return when (httpCode) {
            401 -> UNAUTHORIZED
            404 -> NOT_FOUND
            else -> SOMETHING_WRONG
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
