package com.dominic.coin_search.feature_favorite_list.domain.use_case.get_coins

import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_favorite_list.domain.repository.FavoriteListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCoinsUseCase @Inject constructor(
    private val repository: FavoriteListRepository
) {

     operator fun invoke(): Flow<List<CoinDetailModel>> {
         return repository.getAllCoins()
     }
}