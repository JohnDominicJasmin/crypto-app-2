package com.dominic.coin_search.feature_authentication.presentation.authentication_email

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dominic.coin_search.R
import com.dominic.coin_search.feature_alert_dialog.domain.model.AlertDialogState
import com.dominic.coin_search.feature_alert_dialog.presentation.AlertDialog
import com.dominic.coin_search.feature_alert_dialog.presentation.NoInternetDialog
import com.dominic.coin_search.feature_authentication.presentation.authentication_email.components.EmailAuthResendButton
import com.example.cyclistance.feature_authentication.presentation.authentication_email.components.EmailAuthTextStatus
import com.example.cyclistance.feature_authentication.presentation.authentication_email.components.EmailAuthVerifyEmailButton
import com.example.cyclistance.feature_authentication.presentation.authentication_email.components.emailAuthConstraints
import com.example.cyclistance.feature_authentication.presentation.authentication_email.event.EmailAuthEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_email.event.EmailAuthVmEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_email.event.EmailUiEvent
import com.example.cyclistance.feature_authentication.presentation.authentication_email.state.EmailAuthState
import com.example.cyclistance.feature_authentication.presentation.authentication_email.state.EmailAuthUiState
import com.dominic.coin_search.feature_authentication.presentation.common.AuthenticationConstraintsItem
import com.dominic.coin_search.feature_authentication.presentation.common.visible
import com.dominic.coin_search.navigation.Screens
import com.dominic.coin_search.navigation.navigateScreenInclusively
import com.dominic.coin_search.ui.theme.DashCoinTheme
import com.example.cyclistance.feature_authentication.presentation.authentication_email.EmailAuthViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber


@Composable
fun EmailAuthScreen(
    emailAuthViewModel: EmailAuthViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavController) {


    val context = LocalContext.current

    val emailAuthState by emailAuthViewModel.state.collectAsStateWithLifecycle()
    var uiState by rememberSaveable { mutableStateOf(EmailAuthUiState()) }


    val intent = remember {
        Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_EMAIL)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    val onDismissAlertDialog = remember {
        {
            uiState = uiState.copy(
                alertDialogState = AlertDialogState()
            )
        }
    }

    val onClickVerifyButton = remember {
        {
            runCatching {
                startActivity(context, intent, null)
            }.onFailure {
                Toast.makeText(context, "No email app detected.", Toast.LENGTH_LONG).show()
            }
            Unit
        }
    }

    val onClickResendButton = remember {
        {
            emailAuthViewModel.apply {
                onEvent(EmailAuthVmEvent.ResendEmailVerification)
                onEvent(EmailAuthVmEvent.StartTimer)
                onEvent(EmailAuthVmEvent.SendEmailVerification)
            }
            Unit
        }
    }

    val onDismissNoInternetDialog = remember {
        {
            uiState = uiState.copy(
                isNoInternetVisible = false
            )
        }
    }

    LaunchedEffect(key1 = true) {
        emailAuthViewModel.onEvent(EmailAuthVmEvent.StartTimer)
        emailAuthViewModel.onEvent(EmailAuthVmEvent.SendEmailVerification)
        emailAuthViewModel.onEvent(EmailAuthVmEvent.SubscribeEmailVerification)
    }



    LaunchedEffect(key1 = true) {

        emailAuthViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EmailAuthEvent.EmailVerificationSuccess -> {
                    navController.navigateScreenInclusively(
                        Screens.CoinsScreen.route,
                        Screens.EmailAuthScreen.route)
                }

                is EmailAuthEvent.ReloadEmailFailed -> {
                    Toast.makeText(context, event.reason, Toast.LENGTH_LONG).show()
                }

                is EmailAuthEvent.EmailVerificationNotSent -> {
                    Toast.makeText(context, event.reason, Toast.LENGTH_LONG).show()
                }

                is EmailAuthEvent.NoInternetConnection -> {
                    uiState = uiState.copy(isNoInternetVisible = true)
                }

                is EmailAuthEvent.TimerStarted -> {
                    uiState = uiState.copy(isTimerRunning = true)
                }

                is EmailAuthEvent.TimerStopped -> {
                    uiState = uiState.copy(isTimerRunning = false)
                }

                is EmailAuthEvent.EmailVerificationSent -> {
                    uiState = uiState.copy(
                        alertDialogState = AlertDialogState(
                            title = "New Email Sent.",
                            description = "New verification email has been sent to your email address.",
                            icon = R.raw.success
                        )
                    )
                }

                is EmailAuthEvent.SendEmailVerificationFailed -> {
                    uiState = uiState.copy(
                        alertDialogState = AlertDialogState(
                            title = "Email Verification Failed.",
                            description = "Failed to send verification email. Please try again later.",
                            icon = R.raw.error)
                    )
                }

                else -> {
                    Timber.d("User email is not verified yet. Verification is not success.")
                }
            }
        }

    }


    EmailAuthScreenContent(
        modifier = Modifier.padding(paddingValues),
        emailAuthState = emailAuthState,
        uiState = uiState,
        event = { event ->
            when (event) {
                is EmailUiEvent.DismissAlertDialog -> onDismissAlertDialog()
                is EmailUiEvent.DismissNoInternetDialog -> onDismissNoInternetDialog()
                is EmailUiEvent.VerifyEmail -> onClickVerifyButton()
                is EmailUiEvent.ResendEmail -> onClickResendButton()
            }
        }
    )

}

@Preview()
@Composable
fun EmailAuthScreenPreview() {
    DashCoinTheme(true) {
        EmailAuthScreenContent(
            emailAuthState = EmailAuthState(
                savedAccountEmail = "johndoe@gmail.com",
                secondsLeft = 10,
            ),
        )
    }

}


@Composable
fun EmailAuthScreenContent(
    modifier: Modifier = Modifier,
    emailAuthState: EmailAuthState = EmailAuthState(),
    uiState: EmailAuthUiState = EmailAuthUiState(),
    event: (EmailUiEvent) -> Unit = {}) {


    Surface(
        color = MaterialTheme.colors.background,
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            ConstraintLayout(
                constraintSet = emailAuthConstraints,
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()) {

                Image(
                    contentDescription = "App Icon",
                    painter = painterResource(id = R.drawable.ic_dark_email),
                    modifier = Modifier
                        .height(165.dp)
                        .width(155.dp)
                        .layoutId(AuthenticationConstraintsItem.IconDisplay.layoutId)
                )

                EmailAuthTextStatus(email = emailAuthState.savedAccountEmail)


                if (uiState.alertDialogState.visible()) {
                    AlertDialog(
                        alertDialog = uiState.alertDialogState,
                        onDismissRequest = {
                            event(EmailUiEvent.DismissAlertDialog)
                        }
                    )
                }

                if (emailAuthState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.layoutId(
                            AuthenticationConstraintsItem.ProgressBar.layoutId))
                }


                val secondsRemainingText =
                    remember(uiState.isTimerRunning, emailAuthState.secondsLeft) {
                        if (uiState.isTimerRunning) "Resend Email in ${emailAuthState.secondsLeft}s" else "Resend Email"
                    }

                EmailAuthVerifyEmailButton(
                    onClickVerifyButton = {
                        event(EmailUiEvent.VerifyEmail)
                    },
                    enabled = !emailAuthState.isLoading)

                EmailAuthResendButton(
                    text = secondsRemainingText,
                    isEnabled = !uiState.isTimerRunning && !emailAuthState.isLoading,
                    onClickResendButton = {
                        event(EmailUiEvent.ResendEmail)
                    })

                if (uiState.isNoInternetVisible) {
                    NoInternetDialog(
                        onDismiss = {
                            event(EmailUiEvent.DismissNoInternetDialog)
                        },
                        modifier = Modifier.layoutId(AuthenticationConstraintsItem.NoInternetScreen.layoutId),
                    )
                }
            }

        }
    }
}
