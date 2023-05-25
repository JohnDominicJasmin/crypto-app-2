package com.dominic.coin_search.feature_authentication.presentation.authentication_sign_up.state

import android.os.Parcelable
import com.dominic.coin_search.feature_alert_dialog.domain.model.AlertDialogState
import kotlinx.parcelize.Parcelize


@Parcelize
data class SignUpUiState(
    val email:String = "",
    val emailErrorMessage:String = "",
    val password:String = "",
    val passwordErrorMessage:String = "",
    val confirmPassword:String = "",
    val confirmPasswordErrorMessage:String = "",
    val alertDialogState: AlertDialogState = AlertDialogState(),
    val passwordVisible: Boolean = false,
    val isNoInternetVisible: Boolean = false,
): Parcelable
