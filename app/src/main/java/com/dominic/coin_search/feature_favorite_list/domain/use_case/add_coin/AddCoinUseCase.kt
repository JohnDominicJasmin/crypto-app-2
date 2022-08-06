package com.dominic.coin_search.feature_favorite_list.domain.use_case.add_coin

import com.dominic.coin_search.feature_coins.domain.models.CoinDetailModel
import com.dominic.coin_search.feature_favorite_list.domain.repository.FavoriteListRepository
import javax.inject.Inject

class AddCoinUseCase @Inject constructor(
    private val repository: FavoriteListRepository
) {

    suspend operator fun invoke(coins: CoinDetailModel) {
        repository.insertCoin(coins)
    }
}