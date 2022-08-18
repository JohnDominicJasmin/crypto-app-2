package com.dominic.coin_search.feature_coins.domain.use_case.get_coin

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(coinId: String, currency: String): Flow<CoinDetailModel> = flow {
            emit( repository.getCoinById(coinId, currency))
    }
}