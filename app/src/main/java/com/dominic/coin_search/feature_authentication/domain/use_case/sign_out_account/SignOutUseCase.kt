package com.dominic.coin_search.feature_authentication.domain.use_case.sign_out_account

import com.dominic.coin_search.feature_authentication.domain.repository.AuthRepository

class SignOutUseCase(private val repository: AuthRepository) {

     operator fun invoke() = repository.signOut()
}