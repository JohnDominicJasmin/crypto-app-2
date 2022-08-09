package com.dominic.coin_search.feature_coins.domain.use_case

import com.dominic.coin_search.feature_coins.domain.use_case.get_chart.GetChartUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.get_chart_period.GetChartPeriodUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.get_coin.GetCoinUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.get_coin_information.GetCoinInformationUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.get_coins.GetCoinsUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.get_currency.GetCurrencyUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.get_fiats.GetFiatsUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.get_market_status.GetGlobalMarketUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.get_news.GetNewsUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.update_chart_period.UpdateChartPeriodUseCase
import com.dominic.coin_search.feature_coins.domain.use_case.update_currency.UpdateCurrencyUseCase

data class CoinUseCases (

    val getCoins: GetCoinsUseCase,
    val getCoin: GetCoinUseCase,
    val getChart: GetChartUseCase,
    val getNews: GetNewsUseCase,
    val getGlobalMarket: GetGlobalMarketUseCase,
    val getFiats: GetFiatsUseCase,
    val getCurrency: GetCurrencyUseCase,
    val updateCurrency: UpdateCurrencyUseCase,
    val getChartPeriod: GetChartPeriodUseCase,
    val updateChartPeriod: UpdateChartPeriodUseCase,
    val getCoinInformation: GetCoinInformationUseCase


)