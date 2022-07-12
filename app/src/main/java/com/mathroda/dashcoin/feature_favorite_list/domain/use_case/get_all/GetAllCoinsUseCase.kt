package com.mathroda.dashcoin.feature_favorite_list.domain.use_case.get_all

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_favorite_list.domain.repository.FavoriteListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCoinsUseCase @Inject constructor(
    private val repository: FavoriteListRepository
) {

     operator fun invoke(): Flow<List<CoinDetailModel>> {
         return repository.getAllCoins()
     }
}