package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.ChartTimeSpan
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel

data class CoinDetailState(

    val chartModel: ChartModel? = null,
    val isLoading: Boolean = false,
    val coinChartPeriod: String = ChartTimeSpan.OneDay.value,
    val coinDetailModel: CoinDetailModel? = null,
    val errorMessage: String = "",
    val currencySymbol: String = "$",
    val hasInternet: Boolean = true,
    val isFavorite: Boolean = false,
    val coinId:String = "",
    val chartDate: String = "",
    val chartPrice: String = "",
)

