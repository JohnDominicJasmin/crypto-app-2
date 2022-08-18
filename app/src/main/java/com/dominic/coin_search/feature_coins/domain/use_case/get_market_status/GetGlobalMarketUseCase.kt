package com.dominic.coin_search.feature_coins.domain.use_case.get_market_status

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinGlobalMarketModel
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class GetGlobalMarketUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    suspend operator fun invoke(): CoinGlobalMarketModel {
        return repository.getGlobalMarket()
    }
}