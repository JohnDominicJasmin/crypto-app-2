package com.dominic.coin_search.feature_coins.domain.use_case.get_currency
import com.dominic.coin_search.feature_coins.domain.models.CoinCurrencyPreference
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    suspend operator fun invoke(): Flow<CoinCurrencyPreference> {
        return repository.getCurrency()
    }
}