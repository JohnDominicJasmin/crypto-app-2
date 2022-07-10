package com.mathroda.dashcoin.feature_watch_list.domain.use_case.get_all

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_watch_list.domain.repository.SavedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCoinsUseCase @Inject constructor(
    private val repository: SavedRepository
) {

     operator fun invoke(): Flow<List<CoinDetailModel>> {
         return repository.getAllCoins()
     }
}