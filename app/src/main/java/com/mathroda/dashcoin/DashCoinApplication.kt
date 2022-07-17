package com.mathroda.dashcoin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

@HiltAndroidApp
class DashCoinApplication : Application(){
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main )

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}