package com.dominic.coin_search.feature_authentication.domain.use_case.verify_account

import com.dominic.coin_search.feature_authentication.domain.repository.AuthRepository

class SendEmailVerificationUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke():Boolean =
        repository.sendEmailVerification()

}