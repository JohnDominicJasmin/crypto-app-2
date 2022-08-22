package com.dominic.coin_search.feature_coins.domain.use_case.get_currency_exchange_rate

import com.dominic.coin_search.feature_coins.domain.models.currency.CurrencyExchangeModel
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class GetCurrencyExchangeUseCase @Inject constructor(
    private val repository: CoinRepository) {

    suspend operator fun invoke(currency: String): CurrencyExchangeModel {
        return repository.getCurrencyExchangeRate(currency)
    }
}