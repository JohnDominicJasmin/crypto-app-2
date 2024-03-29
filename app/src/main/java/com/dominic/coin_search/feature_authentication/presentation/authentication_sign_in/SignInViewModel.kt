package com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in

import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.core.util.Constants.FACEBOOK_CONNECTION_FAILURE
import com.dominic.coin_search.core.util.Constants.SIGN_IN_VM_STATE_KEY
import com.dominic.coin_search.feature_authentication.domain.exceptions.AuthExceptions
import com.dominic.coin_search.feature_authentication.domain.model.AuthModel
import com.dominic.coin_search.feature_authentication.domain.model.SignInCredential
import com.dominic.coin_search.feature_authentication.domain.use_case.AuthenticationUseCase
import com.dominic.coin_search.feature_authentication.domain.util.ActivityResultCallbackI
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.event.SignInEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.event.SignInVmEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.state.SignInState
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authUseCase: AuthenticationUseCase,
    private val defaultDispatcher: CoroutineDispatcher
    ) : ViewModel(), ActivityResultCallbackI {

    private var job: Job? = null
    private var callbackManager = CallbackManager.Factory.create()

    private val _state: MutableStateFlow<SignInState> = MutableStateFlow(savedStateHandle[SIGN_IN_VM_STATE_KEY] ?: SignInState())
    val state = _state.asStateFlow()

    private val _eventFlow: MutableSharedFlow<SignInEvent> = MutableSharedFlow()
    val eventFlow: SharedFlow<SignInEvent> = _eventFlow.asSharedFlow()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    init {
        registerFacebookSignInCallback()
        removeFacebookUserAccountPreviousToken()
    }

    fun onEvent(event: SignInVmEvent) {


        when (event) {
            is SignInVmEvent.SignInFacebook -> {

                _state.update { it.copy(isLoading = true) }
                event.activity?.let {
                    LoginManager.getInstance()
                        .logInWithReadPermissions(it, listOf("email", "public_profile", ))
                }
            }

            is SignInVmEvent.SignInGoogle -> {
                signInWithCredential(event.authCredential)
            }

            is SignInVmEvent.SignInWithEmailAndPassword -> {
                signInWithEmailAndPassword(email = event.email, password = event.password)
            }

        }
        savedStateHandle[SIGN_IN_VM_STATE_KEY] = state.value
    }


    private fun signInWithEmailAndPassword(email: String, password: String) {

        viewModelScope.launch(context = defaultDispatcher) {
            runCatching {
                _state.update { it.copy(isLoading = true) }
                with(state.value) {
                    authUseCase.signInWithEmailAndPasswordUseCase(
                        authModel = AuthModel(
                            email = email.trim(),
                            password = password.trim()))
                }

            }.onSuccess { isSignedIn ->
                _state.update { it.copy(isLoading = false) }
                if (isSignedIn) {
                    _eventFlow.emit(SignInEvent.RefreshEmail)
                } else {
                    _eventFlow.emit(SignInEvent.SignInFailed())
                }
            }.onFailure { exception ->
                _state.update { it.copy(isLoading = false) }
                handleException(exception)
            }
        }.invokeOnCompletion {
            savedStateHandle[SIGN_IN_VM_STATE_KEY] = state.value
        }
    }



    private fun signInWithCredential(authCredential: SignInCredential) {
        job?.cancel()
        job = viewModelScope.launch(context = defaultDispatcher) {
            runCatching {
                _state.update { it.copy(isLoading = true) }
                authUseCase.signInWithCredentialUseCase(authCredential)
            }.onSuccess { isSuccess ->
                _state.update { it.copy(isLoading = false) }
                if (isSuccess) {
                    _eventFlow.emit(SignInEvent.SignInSuccess)
                }
            }.onFailure { exception ->
                _state.update { it.copy(isLoading = false) }
                handleException(exception)
            }
        }.apply {
            invokeOnCompletion {
                savedStateHandle[SIGN_IN_VM_STATE_KEY] = state.value
            }
        }

    }

    private suspend fun handleException(exception: Throwable){
        when (exception) {
            is AuthExceptions.EmailException -> {
                _eventFlow.emit(value = SignInEvent.InvalidEmail(exception.message ?: "Email is Invalid."))
            }
            is AuthExceptions.PasswordException -> {
                _eventFlow.emit(value = SignInEvent.InvalidPassword(exception.message ?: "Password is Invalid."))
            }

            is AuthExceptions.TooManyRequestsException -> {
                viewModelScope.launch {
                    _eventFlow.emit(value = SignInEvent.AccountBlockedTemporarily)
                }
            }

            is AuthExceptions.NetworkException -> {
                viewModelScope.launch {
                    _eventFlow.emit(value = SignInEvent.NoInternetConnection)
                }
            }
            is AuthExceptions.ConflictFBTokenException -> {
                viewModelScope.launch {
                    removeFacebookUserAccountPreviousToken()
                    _eventFlow.emit(value = SignInEvent.ConflictFbToken)
                }
            }

            else -> {
                Timber.e("${this@SignInViewModel.javaClass.name}: ${exception.message}")
            }
        }
        savedStateHandle[SIGN_IN_VM_STATE_KEY] = state.value
    }

    // TODO: move to repository
    private fun registerFacebookSignInCallback() {
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    _state.update { it.copy(isLoading = true) }
                    signInWithCredential(authCredential = SignInCredential.Facebook(providerToken =  result.accessToken.token))
                    savedStateHandle[SIGN_IN_VM_STATE_KEY] = state.value
                }

                override fun onCancel() {
                    _state.update { it.copy(isLoading = false) }
                    savedStateHandle[SIGN_IN_VM_STATE_KEY] = state.value
                    Timber.e("facebook:onCancel")
                }

                override fun onError(error: FacebookException) {
                    _state.update { it.copy(isLoading = false) }
                    viewModelScope.launch(context = defaultDispatcher) {
                        error.handleFacebookSignInException()
                    }.invokeOnCompletion {
                        savedStateHandle[SIGN_IN_VM_STATE_KEY] = state.value
                    }
                }
            }
        )
    }


    private suspend fun Exception.handleFacebookSignInException() {

        if (message == FACEBOOK_CONNECTION_FAILURE) {
            _eventFlow.emit(value = SignInEvent.NoInternetConnection)
            return
        }
        message?.let {
            _eventFlow.emit(value = SignInEvent.FacebookSignInFailed)
            removeFacebookUserAccountPreviousToken()
        }
    }

    //todo move this to repository
    private fun removeFacebookUserAccountPreviousToken() {
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut()
        }
    }

    override fun onCleared() {
        super.onCleared()
        LoginManager.getInstance().unregisterCallback(callbackManager)
    }
}