package com.dominic.coin_search.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object ConnectionStatus {
    fun Context.hasInternetConnection(): Boolean {
        return NetworkConnectivityUtil(this).hasInternet()
    }

}