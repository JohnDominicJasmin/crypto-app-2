package com.dominic.coin_search.feature_favorites.domain.use_case.delete_coin

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_favorites.domain.repository.FavoritesRepository
import javax.inject.Inject

class DeleteCoinUseCase @Inject constructor(
    private val repository: FavoritesRepository
) {

    suspend operator fun invoke(coins: CoinDetailModel) {
        repository.deleteCoin(coins)
    }
}