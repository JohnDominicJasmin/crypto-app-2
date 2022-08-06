package com.dominic.coin_search.feature_favorite_list.domain.use_case

import com.dominic.coin_search.feature_favorite_list.domain.use_case.add_coin.AddCoinUseCase
import com.dominic.coin_search.feature_favorite_list.domain.use_case.delete_coin.DeleteCoinUseCase
import com.dominic.coin_search.feature_favorite_list.domain.use_case.get_all.GetAllCoinsUseCase

data class FavoriteUseCase(
    val addCoin: AddCoinUseCase,
    val deleteCoin: DeleteCoinUseCase,
    val getAllCoins: GetAllCoinsUseCase)
