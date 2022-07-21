package com.mathroda.dashcoin.feature_coins.domain.use_case.get_market_status

import com.mathroda.dashcoin.feature_coins.domain.models.GlobalMarketModel
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class GetGlobalMarketUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    suspend operator fun invoke():GlobalMarketModel{
        return repository.getGlobalMarket()
    }
}