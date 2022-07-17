package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel

data class CoinDetailState(
    val chartModel: ChartModel?= null,
    val isLoading: Boolean = false,
    val coinDetailModel: CoinDetailModel?= null,
    val errorMessage: String = "",
    val isFavorite: Boolean = false

    )
