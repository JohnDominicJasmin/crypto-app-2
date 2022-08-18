package com.dominic.coin_search.feature_coins.data.mapper

import com.dominic.coin_search.feature_coins.data.dto.*
import com.dominic.coin_search.feature_coins.data.dto.GlobalMarketDto
import com.dominic.coin_search.feature_coins.domain.models.chart.ChartModel
import com.dominic.coin_search.feature_coins.domain.models.coin.*
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel

object CoinMapper {


    fun ChartDto.toChart(): ChartModel {
        return ChartModel(
            chart
        )
    }


    fun Coin.toCoins(): CoinModel {
        return CoinModel(
            id = id,
            icon = icon,
            marketCap = marketCap,
            name = name,
            price = price,
            priceChange1d = priceChange1d,
            rank = rank,
            symbol = symbol,
            priceChange1h = priceChange1h,
            priceChange1w = priceChange1w,
            volume = volume
        )
    }

    fun CoinDetail.toCoinDetail(): CoinDetailModel {
        return CoinDetailModel(
            availableSupply = availableSupply,
            icon = icon,
            id = id,
            marketCap = marketCap,
            name = name,
            price = price,
            priceChange1d = priceChange1d,
            priceChange1h = priceChange1h,
            priceChange1w = priceChange1w,
            rank = rank,
            symbol = symbol,
            totalSupply = totalSupply,
            twitterUrl = twitterUrl,
            volume = volume,
            websiteUrl = websiteUrl,
            priceBtc = priceBtc
        )
    }


    fun News.toNewsDetail(): NewsModel {
        return NewsModel(
            id = id,
            imgURL = imgURL,
            link = link,
            source = source,
            title = title,
            feedDate = feedDate,
            description = description,
        )
    }


    fun GlobalMarketDto.toGlobalMarket(): CoinGlobalMarketModel {
        return CoinGlobalMarketModel(
            marketCapUsd = marketCapUsd,
            volume24hUsd = volume24hUsd,
            cryptocurrenciesNumber = cryptocurrenciesNumber,
            bitcoinDominancePercentage = bitcoinDominancePercentage,
            marketCapAllTimeHigh = marketCapAthValue,
            volume24hAllTimeHigh = volume24hAthValue,
        )
    }

    fun FiatCurrencyDto.toCoinFiat(): CoinFiatModel {
        return CoinFiatModel(
            currencies = this
        )
    }

    fun CoinInformationDto.toCoinInformation(): CoinInformationModel {
        return CoinInformationModel(
            coinId = id,
            name = name,
            isActive = isActive,
            symbol = symbol,
            description = description,
            rank = rank,
            team = team,
            tags = tags.map { it.name },
        )
    }

}