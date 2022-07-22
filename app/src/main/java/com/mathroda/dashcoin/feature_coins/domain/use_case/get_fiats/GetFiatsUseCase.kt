package com.mathroda.dashcoin.feature_coins.domain.use_case.get_fiats

import com.mathroda.dashcoin.feature_coins.domain.models.CoinFiatModel
import com.mathroda.dashcoin.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class GetFiatsUseCase @Inject constructor(
    private val repository: CoinRepository
){

    suspend operator fun invoke():CoinFiatModel{
        return repository.getFiats()
    }
}