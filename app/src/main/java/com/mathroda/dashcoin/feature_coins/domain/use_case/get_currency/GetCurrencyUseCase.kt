package com.mathroda.dashcoin.feature_coins.domain.use_case.get_currency
import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    suspend operator fun invoke(): Flow<CoinCurrencyPreference> {
        return repository.getCurrency()
    }
}