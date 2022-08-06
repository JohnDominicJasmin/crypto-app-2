package com.dominic.coin_search.core.util

import android.content.Context
import android.net.ConnectivityManager

object ConnectionStatus {
    @Suppress("Deprecation")
    fun hasInternetConnection(context: Context): Boolean =
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.let { networkInfo->
            networkInfo?.isConnected == true && networkInfo.isAvailable
        }
}