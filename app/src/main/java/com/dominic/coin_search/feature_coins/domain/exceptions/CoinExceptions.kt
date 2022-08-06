package com.dominic.coin_search.feature_coins.domain.exceptions

object CoinExceptions {
    class UnexpectedErrorException(message: String = "Unexpected error occurred.") : RuntimeException(message)
    class NoInternetException(message: String = "Couldn't reach server. Check your internet connection") : RuntimeException(message)
}