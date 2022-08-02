package com.mathroda.dashcoin.feature_coins.domain.use_case

import androidx.room.Update
import com.mathroda.dashcoin.feature_coins.domain.models.GlobalMarketModel
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_chart.GetChartUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_chart_period.GetChartPeriodUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_coin.GetCoinUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_coins.GetCoinsUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_currency.GetCurrencyUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_fiats.GetFiatsUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_market_status.GetGlobalMarketUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.get_news.GetNewsUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.update_chart_period.UpdateChartPeriodUseCase
import com.mathroda.dashcoin.feature_coins.domain.use_case.update_currency.UpdateCurrencyUseCase

data class CoinUseCases (

    val getCoins: GetCoinsUseCase,
    val getCoin: GetCoinUseCase,
    val getChart: GetChartUseCase,
    val getNews: GetNewsUseCase,
    val getGlobalMarket: GetGlobalMarketUseCase,
    val getFiats: GetFiatsUseCase,
    val getCurrency: GetCurrencyUseCase,
    val updateCurrency: UpdateCurrencyUseCase,
    val getChartPeriodUseCase: GetChartPeriodUseCase,
    val updateChartPeriodUseCase: UpdateChartPeriodUseCase


)