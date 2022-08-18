package com.dominic.coin_search.feature_coins.domain.use_case.get_fiats

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinFiatModel
import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class GetFiatsUseCase @Inject constructor(
    private val repository: CoinRepository
){

    suspend operator fun invoke(): CoinFiatModel {
        return repository.getFiats()
    }
}