package com.dominic.coin_search.feature_authentication.presentation.authentication_sign_up.event


sealed class SignUpVmEvent{
    data class SignUp(
        val email: String,
        val password: String,
        val confirmPassword: String,
    ): SignUpVmEvent()

    object SignOut: SignUpVmEvent()

}
