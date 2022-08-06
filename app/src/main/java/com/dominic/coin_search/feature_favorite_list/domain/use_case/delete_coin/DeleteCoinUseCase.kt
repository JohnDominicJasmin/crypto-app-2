package com.dominic.coin_search.feature_favorite_list.domain.use_case.delete_coin

import com.dominic.coin_search.feature_coins.domain.models.CoinDetailModel
import com.dominic.coin_search.feature_favorite_list.domain.repository.FavoriteListRepository
import javax.inject.Inject

class DeleteCoinUseCase @Inject constructor(
    private val repository: FavoriteListRepository
) {

    suspend operator fun invoke(coins: CoinDetailModel) {
        repository.deleteCoin(coins)
    }
}