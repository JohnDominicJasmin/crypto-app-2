package com.dominic.coin_search.feature_authentication.domain.model

sealed class SignInCredential(val providerToken: String = "") {
    class Facebook(providerToken: String) : SignInCredential(providerToken = providerToken)

    class Google(providerToken: String) : SignInCredential(providerToken = providerToken)
}
