package com.dominic.coin_search.feature_authentication.domain.use_case.read_account

import com.dominic.coin_search.feature_authentication.domain.repository.AuthRepository

class GetEmailUseCase(private val repository: AuthRepository){
     operator fun invoke():String? {
      return repository.getEmail()
     }
}