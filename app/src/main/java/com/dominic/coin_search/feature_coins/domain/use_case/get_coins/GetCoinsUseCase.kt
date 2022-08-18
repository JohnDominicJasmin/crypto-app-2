package com.dominic.coin_search.feature_coins.domain.use_case.get_coins

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinModel
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(currency:String): Flow<List<CoinModel>> = flow {
        emit(repository.getCoins(currency))
    }
}