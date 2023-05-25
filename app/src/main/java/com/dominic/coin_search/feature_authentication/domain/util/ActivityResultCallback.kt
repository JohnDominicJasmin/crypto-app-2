package com.dominic.coin_search.feature_authentication.domain.util

import android.content.Intent
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.cyclistance.feature_authentication.domain.util.ActivityResultCallbackManager

val LocalActivityResultCallbackManager =
    staticCompositionLocalOf<ActivityResultCallbackManager> { error("No ActivityResultCallbackManager provided") }

interface ActivityResultCallbackI {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean
}