package com.dominic.coin_search.feature_authentication.presentation.authentication_sign_up

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.core.util.Constants.SIGN_UP_VM_STATE_KEY
import com.dominic.coin_search.feature_authentication.domain.exceptions.AuthExceptions
import com.dominic.coin_search.feature_authentication.domain.model.AuthModel
import com.dominic.coin_search.feature_authentication.domain.use_case.AuthenticationUseCase
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_up.event.SignUpVmEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_up.event.SignUpEvent
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_up.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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
class SignUpViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authUseCase: AuthenticationUseCase,
    private val defaultDispatcher: CoroutineDispatcher
    ) : ViewModel() {

    private val _state: MutableStateFlow<SignUpState> = MutableStateFlow(savedStateHandle[SIGN_UP_VM_STATE_KEY] ?: SignUpState())
    val state = _state.asStateFlow()

    private val _eventFlow: MutableSharedFlow<SignUpEvent> = MutableSharedFlow()
    val eventFlow: SharedFlow<SignUpEvent> = _eventFlow.asSharedFlow()

    init {
        _state.update {
            it.copy(
                hasAccountSignedIn = authUseCase.hasAccountSignedInUseCase(),
                savedAccountEmail = authUseCase.getEmailUseCase() ?: "",
            )
        }
        savedStateHandle[SIGN_UP_VM_STATE_KEY] = state.value

    }

    fun onEvent(event: SignUpVmEvent) {
        when (event) {

            is SignUpVmEvent.SignUp -> {
                signUp(
                    email = event.email,
                    password = event.password,
                    confirmPassword = event.confirmPassword
                )
            }

            is SignUpVmEvent.SignOut -> {
                authUseCase.signOutUseCase()
            }
        }
        savedStateHandle[SIGN_UP_VM_STATE_KEY] = state.value
    }


    private fun signUp(
         email: String,
         password: String,
         confirmPassword: String) {

        viewModelScope.launch(context = defaultDispatcher) {
            runCatching {
                _state.update { it.copy(isLoading = true) }
                with(state.value) {
                    authUseCase.createWithEmailAndPasswordUseCase(
                        authModel = AuthModel(
                            email = email.trim(),
                            password = password.trim(),
                            confirmPassword = confirmPassword.trim()
                        ))
                }
            }.onSuccess { isAccountCreated ->
                _state.update { it.copy(isLoading = false) }
                if (isAccountCreated) {
                    _eventFlow.emit(SignUpEvent.SignUpSuccess)
                } else {
                    _eventFlow.emit(SignUpEvent.CreateAccountFailed())
                }
            }.onFailure { exception ->
                _state.update { it.copy(isLoading = false) }
                handleException(exception)
            }
        }.invokeOnCompletion {
            savedStateHandle[SIGN_UP_VM_STATE_KEY] = state.value
        }
    }

    private suspend fun handleException(exception: Throwable) {
        when (exception) {
            is AuthExceptions.EmailException -> {
                _eventFlow.emit(value = SignUpEvent.InvalidEmail(reason = exception.message ?: "Invalid email. Please try again."))
            }
            is AuthExceptions.PasswordException -> {
                _eventFlow.emit(value = SignUpEvent.InvalidPassword(reason = exception.message ?: "Invalid password. Please try again."))
            }
            is AuthExceptions.ConfirmPasswordException -> {
                _eventFlow.emit(value = SignUpEvent.InvalidConfirmPassword(reason = exception.message ?: "Passwords do not match. Please try again."))
            }
            is AuthExceptions.NetworkException -> {
                _eventFlow.emit(value = SignUpEvent.NoInternetConnection)
            }
            is AuthExceptions.UserAlreadyExistsException -> {
                _eventFlow.emit(value = SignUpEvent.AccountAlreadyTaken)
            }
            else -> {
                Timber.e("${this@SignUpViewModel.javaClass.name}: ${exception.message}")
            }
        }
        savedStateHandle[SIGN_UP_VM_STATE_KEY] = state.value
    }

}