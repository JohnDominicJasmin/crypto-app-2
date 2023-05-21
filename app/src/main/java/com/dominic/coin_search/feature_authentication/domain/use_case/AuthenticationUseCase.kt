package com.dominic.coin_search.feature_authentication.domain.use_case

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

data class AuthenticationUseCase(
    val reloadEmailUseCase: ReloadEmailUseCase,
    val signOutUseCase: SignOutUseCase,
    val createWithEmailAndPasswordUseCase: CreateWithEmailAndPasswordUseCase,
    val getEmailUseCase: GetEmailUseCase,

    val getIdUseCase: GetIdUseCase,
    val hasAccountSignedInUseCase: HasAccountSignedInUseCase,
    val isEmailVerifiedUseCase: IsEmailVerifiedUseCase,
    val isSignedInWithProviderUseCase: IsSignedInWithProviderUseCase,
    val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase,
    val signInWithCredentialUseCase: SignInWithCredentialUseCase,

    )
