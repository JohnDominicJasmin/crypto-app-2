package com.dominic.coin_search.feature_favorites.domain.use_case

import com.dominic.coin_search.feature_favorites.domain.use_case.add_coin.AddCoinUseCase
import com.dominic.coin_search.feature_favorites.domain.use_case.add_news.AddNewsUseCase
import com.dominic.coin_search.feature_favorites.domain.use_case.delete_coin.DeleteCoinUseCase
import com.dominic.coin_search.feature_favorites.domain.use_case.delete_news.DeleteNewsUseCase
import com.dominic.coin_search.feature_favorites.domain.use_case.get_coins.GetAllCoinsUseCase
import com.dominic.coin_search.feature_favorites.domain.use_case.get_news.GetNewsUseCase

data class FavoriteUseCase(
    val addCoin: AddCoinUseCase,
    val deleteCoin: DeleteCoinUseCase,
    val getCoins: GetAllCoinsUseCase,
    val addNews: AddNewsUseCase,
    val deleteNews: DeleteNewsUseCase,
    val getNews: GetNewsUseCase


    )
