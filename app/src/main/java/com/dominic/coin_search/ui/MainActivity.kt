package com.dominic.coin_search.ui

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.dominic.coin_search.feature_authentication.domain.util.LocalActivityResultCallbackManager
import com.dominic.coin_search.navigation.MainScreen
import com.dominic.coin_search.ui.theme.DashCoinTheme
import com.example.cyclistance.feature_authentication.domain.util.ActivityResultCallbackManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var callbackManager = ActivityResultCallbackManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(application);
        Timber.e( "Signature ${FacebookSdk.getApplicationSignature(this)}")
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContent {

            CompositionLocalProvider(LocalActivityResultCallbackManager provides callbackManager) {
                DashCoinTheme(darkTheme = true) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        MainScreen()
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (!callbackManager.onActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}


