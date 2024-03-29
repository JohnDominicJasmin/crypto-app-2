package com.dominic.coin_search.feature_intro_slider_screen.presentation.components

import com.dominic.coin_search.R

sealed class IntroSliderScreenItem(
    val image: Int,
    val title: String,
    val description: String) {


    object CoinGlobalMarket : IntroSliderScreenItem(
        image = R.drawable.coin_market,
        title = "Coin Global Market Updates",
        description = "Stay up-to-date, track cryptocurrencies, and make informed investment decisions with up-to-date trends and interactive charts."
    )

    object CoinNewsUpdates : IntroSliderScreenItem(
        image = R.drawable.coin_news_updates,
        title = "Coin News Updates",
        description = "Delivers real-time and comprehensive cryptocurrency updates, including news aggregation, empowering users to make informed decisions in the dynamic world of cryptocurrencies."
    )

    object ExchangeCurrency : IntroSliderScreenItem(
        image = R.drawable.ic_currency,
        title = "Exchange Rate to any Currency",
        description = "Effortlessly compare the value of cryptocurrencies against a comprehensive selection of national and international currencies, providing accurate and up-to-date conversion rates for traders, investors, and the curious."
    )

    object StockMarket: IntroSliderScreenItem(
        image = R.drawable.stock_market,
        title = "Stock Market",
        description = "Stay ahead in the world of company stocks with real-time updates, intuitive charting tools, and valuable insights for informed financial decision-making."
    )
}
