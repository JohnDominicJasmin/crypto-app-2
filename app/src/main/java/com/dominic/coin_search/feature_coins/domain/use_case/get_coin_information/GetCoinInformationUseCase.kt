package com.dominic.coin_search.feature_coins.domain.use_case.get_coin_information

import com.dominic.coin_search.feature_coins.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinInformationUseCase@Inject constructor(
    private val repository: CoinRepository) {

    suspend operator fun invoke(coinId: String) = repository.getCoinInformation(coinId)
}