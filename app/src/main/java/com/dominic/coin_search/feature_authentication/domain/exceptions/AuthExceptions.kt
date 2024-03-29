package com.dominic.coin_search.feature_authentication.domain.exceptions

sealed class AuthExceptions {
    class NetworkException(message: String) : RuntimeException(message)
    class UserAlreadyExistsException(val title: String, message: String) : RuntimeException(message)
    class EmailVerificationException(message: String) : RuntimeException(message)
    class TooManyRequestsException(val title: String, message: String) : RuntimeException(message)
    class ConflictFBTokenException(message: String) : RuntimeException(message)
    class PasswordException(message: String) : RuntimeException(message)
    class ConfirmPasswordException(message: String) : RuntimeException(message)
    class EmailException(message: String) : RuntimeException(message)
    class InternalServerException(message: String) : RuntimeException(message)
    class UserException(message: String) : RuntimeException(message)
}
