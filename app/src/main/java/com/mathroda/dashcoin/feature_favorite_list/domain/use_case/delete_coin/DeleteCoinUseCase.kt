package com.mathroda.dashcoin.feature_favorite_list.domain.use_case.delete_coin

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_favorite_list.domain.repository.FavoriteListRepository
import javax.inject.Inject

class DeleteCoinUseCase @Inject constructor(
    private val repository: FavoriteListRepository
) {

    suspend operator fun invoke(coins: CoinDetailModel) {
        repository.deleteCoin(coins)
    }
}