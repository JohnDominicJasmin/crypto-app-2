package com.mathroda.dashcoin.feature_watch_list.domain.use_case.add_coin

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_watch_list.domain.repository.SavedRepository
import javax.inject.Inject

class AddCoinUseCase @Inject constructor(
    private val repository: SavedRepository
) {

    suspend operator fun invoke(coins: CoinDetailModel) {
        repository.insertCoin(coins)
    }
}