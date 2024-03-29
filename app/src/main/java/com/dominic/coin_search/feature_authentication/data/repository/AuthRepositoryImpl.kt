package com.dominic.coin_search.feature_authentication.data.repository

import android.content.Context
import com.dominic.coin_search.R
import com.dominic.coin_search.core.util.ConnectionStatus.hasInternetConnection
import com.dominic.coin_search.core.util.Constants.FACEBOOK_CONNECTION_FAILURE
import com.dominic.coin_search.core.util.Constants.USER_NOT_FOUND
import com.dominic.coin_search.core.util.extension.dataStore


import com.dominic.coin_search.feature_authentication.domain.exceptions.AuthExceptions
import com.dominic.coin_search.feature_authentication.domain.model.SignInCredential
import com.dominic.coin_search.feature_authentication.domain.repository.AuthRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class AuthRepositoryImpl(
    private val context: Context,
    private val auth: FirebaseAuth,
    ) : AuthRepository {

    private var dataStore = context.dataStore



    override suspend fun reloadEmail(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            auth.currentUser?.reload()?.addOnCompleteListener { reload ->
                reload.exception?.let { exception ->
                    if (exception is FirebaseNetworkException) {
                        continuation.resumeWithException(
                            AuthExceptions.NetworkException(
                                message = context.getString(
                                    R.string.no_internet_message)))
                    }
                }
                if (continuation.isActive) {
                    continuation.resume(reload.isSuccessful)
                }
            }
        }
    }

    override suspend fun sendEmailVerification(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { sendEmail ->
                sendEmail.exception?.let {
                    continuation.resumeWithException(
                        AuthExceptions.EmailVerificationException(message = context.getString(R.string.failed_email_verification)))
                }
                if (continuation.isActive) {
                    continuation.resume(sendEmail.isSuccessful)
                }
            }
        }
    }

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean {

        if(!context.hasInternetConnection()){
            throw AuthExceptions.NetworkException(message = context.getString(R.string.no_internet_message))
        }

        return suspendCancellableCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener { createAccount ->
                    createAccount.exception?.let { exception ->
                        if (exception is FirebaseNetworkException) {
                            continuation.resumeWithException(
                                AuthExceptions.NetworkException(
                                    message = context.getString(R.string.no_internet_message)))
                            return@addOnCompleteListener
                        }

                        if (exception is FirebaseAuthUserCollisionException) {
                            continuation.resumeWithException(
                                AuthExceptions.UserAlreadyExistsException(
                                    title = context.getString(R.string.userAlreadyExists),
                                    message = context.getString(R.string.accountAlreadyInUse)))
                            return@addOnCompleteListener
                        }
                        continuation.resumeWithException(exception)
                    }
                    if (continuation.isActive) {
                        continuation.resume(createAccount.isSuccessful)
                    }
                }
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Boolean {

        if(!context.hasInternetConnection()){
            throw AuthExceptions.NetworkException(message = context.getString(R.string.no_internet_message))
        }

        return suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email.trim(), password.trim())
                .addOnCompleteListener { signInWithEmailAndPassword ->
                    signInWithEmailAndPassword.exception?.let { exception ->
                        Timber.e(exception.message)
                        if (exception is FirebaseNetworkException) {
                            continuation.resumeWithException(
                                AuthExceptions.NetworkException(
                                    message = context.getString(
                                        R.string.no_internet_message)))
                            return@addOnCompleteListener
                        }

                        if (exception is FirebaseAuthInvalidCredentialsException) {
                            continuation.resumeWithException(
                                AuthExceptions.PasswordException(
                                    message = context.getString(
                                        R.string.incorrectPasswordMessage)))
                            return@addOnCompleteListener
                        }

                        if (exception is FirebaseAuthInvalidUserException) {
                            if (exception.errorCode == USER_NOT_FOUND) {
                                continuation.resumeWithException(
                                    AuthExceptions.EmailException(
                                        message = context.getString(
                                            R.string.couldntFindAccount)))
                                return@addOnCompleteListener
                            }
                        }

                        if (exception is FirebaseTooManyRequestsException) {
                            continuation.resumeWithException(
                                AuthExceptions.TooManyRequestsException(
                                    title = context.getString(
                                        R.string.tooManyFailedAttempts),
                                    message = context.getString(R.string.manyFailedAttempts)))
                            return@addOnCompleteListener
                        }

                        if (exception is IllegalStateException) {
                            Timber.e(exception.message)
                        }

                        continuation.resumeWithException(exception)
                    }
                    if (continuation.isActive) {
                        continuation.resume(signInWithEmailAndPassword.isSuccessful)
                    }
                }
        }
    }


    override suspend fun signInWithCredential(credential: SignInCredential): Boolean {
        return suspendCancellableCoroutine { continuation ->

            val signInCredential = when(credential){
                is SignInCredential.Google -> GoogleAuthProvider.getCredential(credential.providerToken, null)
                is SignInCredential.Facebook -> FacebookAuthProvider.getCredential(credential.providerToken)
            }

            auth.signInWithCredential(signInCredential)
                .addOnCompleteListener { signInWithCredential ->
                    signInWithCredential.exception?.let { exception ->
                        if (exception.message == FACEBOOK_CONNECTION_FAILURE) {
                            continuation.resumeWithException(
                                AuthExceptions.NetworkException(
                                    message = context.getString(
                                        R.string.no_internet_message)))
                        }

                        if(exception is FirebaseNetworkException){
                            continuation.resumeWithException(
                                AuthExceptions.NetworkException(
                                    message = context.getString(
                                        R.string.no_internet_message)))
                            return@addOnCompleteListener
                        }

                        continuation.resumeWithException(
                            AuthExceptions.ConflictFBTokenException(
                                exception.message
                                ?: "Sorry, something went wrong. Please try again."))
                    }
                    if (continuation.isActive) {
                        continuation.resume(signInWithCredential.isSuccessful)
                    }
                }
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getId(): String? {
        return auth.currentUser?.uid
    }

    override fun getEmail(): String? {
        return auth.currentUser?.email
    }

    override fun isSignedInWithProvider(): Boolean? {
        return auth.currentUser?.providerData?.any {
            it.providerId == FacebookAuthProvider.PROVIDER_ID ||
            it.providerId == GoogleAuthProvider.PROVIDER_ID
        }
    }

    override fun isEmailVerified(): Boolean? {
        return auth.currentUser?.isEmailVerified
    }

    override fun hasAccountSignedIn(): Boolean {
        return auth.currentUser != null
    }



}