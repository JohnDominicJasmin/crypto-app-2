package com.dominic.coin_search.core.di

import android.content.Context
import com.dominic.coin_search.BuildConfig
import com.dominic.coin_search.feature_authentication.data.repository.AuthRepositoryImpl
import com.dominic.coin_search.feature_authentication.domain.repository.AuthRepository
import com.dominic.coin_search.feature_authentication.domain.use_case.AuthenticationUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.create_account.CreateWithEmailAndPasswordUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.create_account.SignInWithCredentialUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.create_account.SignInWithEmailAndPasswordUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.read_account.GetEmailUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.read_account.GetIdUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.sign_out_account.SignOutUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.verify_account.HasAccountSignedInUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.verify_account.IsEmailVerifiedUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.verify_account.IsSignedInWithProviderUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.verify_account.ReloadEmailUseCase
import com.dominic.coin_search.feature_authentication.domain.use_case.verify_account.SendEmailVerificationUseCase

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    //emulator host = 10.0.2.2


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance().apply {
            if (BuildConfig.DEBUG) {
//                useEmulator("192.168.18.21", 9099)
            }
        }
    }


    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        firebaseAuth: FirebaseAuth): AuthRepository {

        return AuthRepositoryImpl(
            context = context,
            auth = firebaseAuth)
    }


    @Provides
    @Singleton
    fun provideAuthenticationUseCase(repository: AuthRepository): AuthenticationUseCase =
        AuthenticationUseCase(
            reloadEmailUseCase = ReloadEmailUseCase(repository = repository),
            signOutUseCase = SignOutUseCase(repository = repository),
            createWithEmailAndPasswordUseCase = CreateWithEmailAndPasswordUseCase(repository = repository),
            getEmailUseCase = GetEmailUseCase(repository = repository),
            getIdUseCase = GetIdUseCase(repository = repository),
            hasAccountSignedInUseCase = HasAccountSignedInUseCase(repository = repository),
            isEmailVerifiedUseCase = IsEmailVerifiedUseCase(repository = repository),
            isSignedInWithProviderUseCase = IsSignedInWithProviderUseCase(repository = repository),
            sendEmailVerificationUseCase = SendEmailVerificationUseCase(repository = repository),
            signInWithEmailAndPasswordUseCase = SignInWithEmailAndPasswordUseCase(repository = repository),
            signInWithCredentialUseCase = SignInWithCredentialUseCase(repository = repository),
        )


}