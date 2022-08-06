package com.dominic.coin_search.feature_coins.domain.use_case.update_currency

import com.dominic.coin_search.feature_coins.domain.models.CoinCurrencyPreference
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class UpdateCurrencyUseCase @Inject constructor(
    private val repository: CoinRepository
){

    suspend operator fun invoke(coinCurrencyPreference: CoinCurrencyPreference){
        repository.updateCurrency(coinCurrencyPreference)
    }

}