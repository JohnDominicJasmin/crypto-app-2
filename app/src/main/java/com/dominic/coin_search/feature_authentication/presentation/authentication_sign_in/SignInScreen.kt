package com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.dominic.coin_search.feature_alert_dialog.domain.model.AlertDialogState
import com.dominic.coin_search.feature_alert_dialog.presentation.AlertDialog
import com.dominic.coin_search.feature_alert_dialog.presentation.NoInternetDialog
import com.dominic.coin_search.feature_authentication.domain.model.SignInCredential
import com.dominic.coin_search.feature_authentication.domain.util.AuthResult
import com.dominic.coin_search.feature_authentication.domain.util.LocalActivityResultCallbackManager
import com.example.cyclistance.feature_authentication.domain.util.findActivity
import com.example.cyclistance.feature_authentication.presentation.authentication_email.EmailAuthViewModel
import com.example.cyclistance.feature_authentication.presentation.authentication_email.event.EmailAuthEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_email.event.EmailAuthVmEvent
import com.example.cyclistance.feature_authentication.presentation.authentication_email.state.EmailAuthState
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.components.SignInButton
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.components.SignInClickableText
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.components.SignInGoogleAndFacebookSection
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.components.SignInTextFieldsArea
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.components.SignUpTextArea
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.components.signInConstraints
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.event.SignInEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.event.SignInVmEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.event.SignUiEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.state.SignInState
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.state.SignInUiState
import com.dominic.coin_search.feature_authentication.presentation.common.AuthenticationConstraintsItem
import com.dominic.coin_search.feature_authentication.presentation.common.visible
import com.dominic.coin_search.R
import com.dominic.coin_search.core.util.Constants.GOOGLE_SIGN_IN_REQUEST_CODE
import com.dominic.coin_search.navigation.Screens
import com.dominic.coin_search.navigation.navigateScreen
import com.dominic.coin_search.navigation.navigateScreenInclusively
import com.dominic.coin_search.ui.theme.DashCoinTheme
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    emailAuthViewModel: EmailAuthViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    navController: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val signInState by signInViewModel.state.collectAsStateWithLifecycle()
    val emailAuthState by emailAuthViewModel.state.collectAsStateWithLifecycle()
    val focusRequester = remember { FocusRequester() }
    var uiState by rememberSaveable {
        mutableStateOf(SignInUiState())
    }

    val focusManager = LocalFocusManager.current
    val authResultLauncher = rememberLauncherForActivityResult(contract = AuthResult()) { task ->
        try {
            val account: GoogleSignInAccount? = task?.getResult(ApiException::class.java)
            account?.idToken?.let { token ->
                scope.launch {
                    signInViewModel.onEvent(
                        event = SignInVmEvent.SignInGoogle(
                            authCredential = SignInCredential.Google(
                                providerToken = token)))
                }
            }
        } catch (e: ApiException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    val callbackManager = LocalActivityResultCallbackManager.current
    DisposableEffect(Unit) {
        callbackManager.addListener(signInViewModel)
        onDispose {
            callbackManager.removeListener(signInViewModel)
        }
    }



    LaunchedEffect(key1 = true) {
        focusRequester.requestFocus()
        signInViewModel.eventFlow.collectLatest { signInEvent ->

            when (signInEvent) {

                is SignInEvent.RefreshEmail -> {
                    emailAuthViewModel.onEvent(EmailAuthVmEvent.RefreshEmail)
                }

                is SignInEvent.SignInSuccess -> {
                    navController.navigateScreenInclusively(
                        Screens.CoinsScreen.route,
                        Screens.SignInScreen.route)
                }

                is SignInEvent.SignInFailed -> {
                    Toast.makeText(context, signInEvent.reason, Toast.LENGTH_SHORT).show()
                }

                is SignInEvent.NoInternetConnection -> {
                    uiState = uiState.copy(
                        isNoInternetVisible = true
                    )
                }

                is SignInEvent.AccountBlockedTemporarily -> {
                    uiState = uiState.copy(
                        alertDialogState = AlertDialogState(
                            title = "Account Blocked Temporarily",
                            description = "You have been blocked temporarily for too many failed attempts. Please try again later.",
                            icon = R.raw.error,
                        )
                    )
                }

                is SignInEvent.ConflictFbToken -> {
                    uiState = uiState.copy(
                        alertDialogState = AlertDialogState(
                            title = "Conflict Facebook Account",
                            description = "Sorry, something went wrong. Please try again.",
                            icon = R.raw.error)
                    )
                }

                is SignInEvent.FacebookSignInFailed -> {
                    uiState = uiState.copy(
                        alertDialogState = AlertDialogState(
                            title = "Facebook Sign In Failed",
                            description = "Failed to sign in with Facebook. Please try again.",
                            icon = R.raw.error)
                    )
                }

                is SignInEvent.InvalidEmail -> {
                    uiState = uiState.copy(
                        emailErrorMessage = signInEvent.reason
                    )
                }

                is SignInEvent.InvalidPassword -> {
                    uiState = uiState.copy(
                        passwordErrorMessage = signInEvent.reason
                    )
                }

            }
        }
    }


    LaunchedEffect(key1 = true) {
        emailAuthViewModel.eventFlow.collectLatest { emailAuthEvent ->
            when (emailAuthEvent) {
                is EmailAuthEvent.EmailVerificationSuccess -> {
                    navController.navigateScreenInclusively(
                        Screens.CoinsScreen.route,
                        Screens.SignInScreen.route)
                }

                is EmailAuthEvent.ReloadEmailFailed -> {
                    Toast.makeText(context, emailAuthEvent.reason, Toast.LENGTH_LONG).show()
                }

                is EmailAuthEvent.EmailVerificationNotSent -> {
                    Toast.makeText(context, emailAuthEvent.reason, Toast.LENGTH_LONG).show()
                }

                is EmailAuthEvent.EmailVerificationFailed -> {
                    navController.navigateScreenInclusively(
                        Screens.EmailAuthScreen.route,
                        Screens.SignInScreen.route)
                }

                is EmailAuthEvent.EmailVerificationSent -> {
                    uiState = uiState.copy(
                        alertDialogState = AlertDialogState(
                            title = "New Email Sent.",
                            description = "New verification email has been sent to your email address.",
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

                else -> {}
            }
        }
    }


    val onDismissAlertDialog = remember {
        {
            uiState = uiState.copy(
                alertDialogState = AlertDialogState()
            )
        }
    }
    val onDoneKeyboardAction = remember {
        {
            signInViewModel.onEvent(
                SignInVmEvent.SignInWithEmailAndPassword(
                    email = uiState.email,
                    password = uiState.password))
            focusManager.clearFocus()
        }
    }

    val onValueChangeEmail = remember<(String) -> Unit> {
        {
            uiState = uiState.copy(
                email = it,
                emailErrorMessage = ""
            )
        }
    }

    val onValueChangePassword = remember<(String) -> Unit> {
        {
            uiState = uiState.copy(
                password = it,
                passwordErrorMessage = ""
            )
        }
    }

    val onClickPasswordVisibility = remember {
        {
            uiState = uiState.copy(
                isPasswordVisible = !uiState.isPasswordVisible
            )
        }
    }

    val onClickFacebookButton = remember {
        {
            signInViewModel.onEvent(SignInVmEvent.SignInFacebook(activity = context.findActivity()))
        }
    }

    val onClickGoogleButton = remember {
        {
            scope.launch {
                authResultLauncher.launch(GOOGLE_SIGN_IN_REQUEST_CODE)
            }
            Unit
        }
    }

    val onClickSignInButton = remember {
        {
            signInViewModel.onEvent(
                SignInVmEvent.SignInWithEmailAndPassword(
                    email = uiState.email,
                    password = uiState.password
                ))
        }
    }

    val onClickSignInText = remember {
        {
            navController.navigateScreen(Screens.SignUpScreen.route)
        }
    }

    val onDismissNoInternetDialog = remember {
        {
            uiState = uiState.copy(
                isNoInternetVisible = false
            )
        }
    }

    SignInScreenContent(
        modifier = Modifier.padding(paddingValues),
        signInState = signInState,
        emailAuthState = emailAuthState,
        focusRequester = focusRequester,
        uiState = uiState,
        event = { event ->
            when (event) {
                is SignUiEvent.DismissAlertDialog -> onDismissAlertDialog()
                is SignUiEvent.KeyboardActionDone -> onDoneKeyboardAction()
                is SignUiEvent.ChangeEmail -> onValueChangeEmail(event.email)
                is SignUiEvent.ChangePassword -> onValueChangePassword(event.password)
                is SignUiEvent.TogglePasswordVisibility -> onClickPasswordVisibility()
                is SignUiEvent.SignInWithFacebook -> onClickFacebookButton()
                is SignUiEvent.SignInWithGoogle -> onClickGoogleButton()
                is SignUiEvent.SignInWithEmailAndPassword -> onClickSignInButton()
                is SignUiEvent.NavigateToSignUp -> onClickSignInText()
                is SignUiEvent.DismissNoInternetDialog -> onDismissNoInternetDialog()
            }
        }
    )
}

@Preview
@Composable
fun SignInScreenPreview() {
    DashCoinTheme(true) {
        SignInScreenContent()
    }
}


@Composable
fun SignInScreenContent(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester(),
    signInState: SignInState = SignInState(),
    emailAuthState: EmailAuthState = EmailAuthState(),
    uiState: SignInUiState = SignInUiState(),
    event: (SignUiEvent) -> Unit = {}) {


    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

        ConstraintLayout(
            constraintSet = signInConstraints,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {

            Spacer(modifier = Modifier.layoutId(AuthenticationConstraintsItem.TopSpacer.layoutId))

            Image(
                contentDescription = "App Icon",
                painter = painterResource(R.drawable.ic_app_icon),
                modifier = Modifier
                    .height(100.dp)
                    .width(90.dp)
                    .layoutId(AuthenticationConstraintsItem.IconDisplay.layoutId)
            )


            SignUpTextArea()

            if (uiState.alertDialogState.visible()) {
                AlertDialog(
                    alertDialog = uiState.alertDialogState,
                    onDismissRequest = {
                        event(SignUiEvent.DismissAlertDialog)
                    })
            }



            SignInTextFieldsArea(
                focusRequester = focusRequester,
                state = signInState,
                keyboardActionOnDone = {
                    event(SignUiEvent.KeyboardActionDone)
                },
                onValueChangeEmail = { event(SignUiEvent.ChangeEmail(it)) },
                onValueChangePassword = { event(SignUiEvent.ChangePassword(it)) },
                onClickPasswordVisibility = { event(SignUiEvent.TogglePasswordVisibility) },
                email = uiState.email,
                emailErrorMessage = uiState.emailErrorMessage,
                password = uiState.password,
                passwordErrorMessage = uiState.passwordErrorMessage,
                passwordVisible = uiState.isPasswordVisible
            )

            val isLoading = remember(signInState.isLoading, emailAuthState.isLoading) {
                (signInState.isLoading || emailAuthState.isLoading)
            }

            SignInGoogleAndFacebookSection(
                onClickFacebookButton = { event(SignUiEvent.SignInWithFacebook) },
                onClickGoogleButton = { event(SignUiEvent.SignInWithGoogle) },
                enabled = !isLoading
            )

            SignInButton(
                onClickSignInButton = { event(SignUiEvent.SignInWithEmailAndPassword) },
                enabled = !isLoading)

            SignInClickableText(
                onClickSignInText = { event(SignUiEvent.NavigateToSignUp) },
                enabled = !isLoading)

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.layoutId(AuthenticationConstraintsItem.ProgressBar.layoutId)
                )
            }

            if (uiState.isNoInternetVisible) {
                NoInternetDialog(
                    onDismiss = {
                        event(SignUiEvent.DismissNoInternetDialog)
                    },
                    modifier = Modifier.layoutId(AuthenticationConstraintsItem.NoInternetScreen.layoutId))

            }

        }
    }
}





