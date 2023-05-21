package com.dominic.coin_search.feature_authentication.domain.use_case.create_account

import com.dominic.coin_search.core.util.resource_texts.ResourceText
import com.dominic.coin_search.core.util.validation.InputValidate.isEmailValid
import com.dominic.coin_search.feature_authentication.domain.exceptions.AuthExceptions
import com.dominic.coin_search.feature_authentication.domain.model.AuthModel
import com.dominic.coin_search.feature_authentication.domain.repository.AuthRepository

class SignInWithEmailAndPasswordUseCase(
    private val repository: AuthRepository) {

    suspend operator fun invoke(authModel: AuthModel):Boolean {

        val email = authModel.email.trim()
        val password = authModel.password.trim()

        return when {
            email.isEmpty() ->
                throw AuthExceptions.EmailException(message = ResourceText.FieldLeftBlank().message)

            !email.isEmailValid() ->
                throw AuthExceptions.EmailException(message = ResourceText.EmailIsInvalid().message)

            password.isEmpty() ->
                throw AuthExceptions.PasswordException(message = ResourceText.FieldLeftBlank().message)


            else -> repository.signInWithEmailAndPassword(email, password)
        }
    }
}