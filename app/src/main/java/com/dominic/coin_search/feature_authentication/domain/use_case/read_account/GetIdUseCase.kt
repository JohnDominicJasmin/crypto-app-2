package com.dominic.coin_search.feature_authentication.domain.use_case.read_account

import com.dominic.coin_search.feature_authentication.domain.exceptions.AuthExceptions
import com.dominic.coin_search.feature_authentication.domain.repository.AuthRepository

class GetIdUseCase(private val repository: AuthRepository) {
    operator fun invoke():String {
        return repository.getId().takeIf { !it.isNullOrEmpty() }
               ?: throw AuthExceptions.UserException(message = "Id not found!")
    }
}