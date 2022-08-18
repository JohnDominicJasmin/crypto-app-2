package com.dominic.coin_search.feature_coins.presentation.coin_detail

import com.dominic.coin_search.feature_coins.domain.models.chart.ChartModel
import com.dominic.coin_search.feature_coins.domain.models.chart.ChartTimeSpan
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinInformationModel

data class CoinDetailState(

    val chartModel: ChartModel? = null,
    val isLoading: Boolean = false,
    val coinChartPeriod: String = ChartTimeSpan.OneDay.value,
    val coinDetailModel: CoinDetailModel? = null,
    val errorMessage: String = "",
    val hasInternet: Boolean = true,
    val isFavorite: Boolean = false,
    val coinId:String = "",
    val chartDate: String = "",
    val chartPrice: String = "",
    val coinInformation: CoinInformationModel? = null
)

