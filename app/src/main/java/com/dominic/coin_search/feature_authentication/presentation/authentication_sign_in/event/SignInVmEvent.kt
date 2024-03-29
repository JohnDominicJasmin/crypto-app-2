package com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.event

import android.app.Activity
import com.dominic.coin_search.feature_authentication.domain.model.SignInCredential

sealed class SignInVmEvent {
    data class SignInFacebook(val activity: Activity?) : SignInVmEvent()
    data class SignInGoogle(var authCredential: SignInCredential.Google) : SignInVmEvent()
    data class SignInWithEmailAndPassword(val email: String, val password: String) : SignInVmEvent()
}