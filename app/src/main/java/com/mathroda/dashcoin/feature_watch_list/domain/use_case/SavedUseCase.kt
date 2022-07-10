package com.mathroda.dashcoin.feature_watch_list.domain.use_case

import com.mathroda.dashcoin.feature_watch_list.domain.use_case.add_coin.AddCoinUseCase
import com.mathroda.dashcoin.feature_watch_list.domain.use_case.delete_coin.DeleteCoinUseCase
import com.mathroda.dashcoin.feature_watch_list.domain.use_case.get_all.GetAllCoinsUseCase

data class SavedUseCase(
    val addCoin: AddCoinUseCase,
    val deleteCoin: DeleteCoinUseCase,
    val getAllCoins: GetAllCoinsUseCase)
