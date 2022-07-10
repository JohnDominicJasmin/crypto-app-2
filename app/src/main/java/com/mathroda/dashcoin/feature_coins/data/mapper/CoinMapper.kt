package com.mathroda.dashcoin.feature_coins.data.mapper

import com.mathroda.dashcoin.feature_coins.data.dto.*
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.domain.models.NewsDetailModel

object CoinMapper {


    fun ChartDto.toChart(): ChartModel{
        return ChartModel(
            chart
        )
    }


    fun Coin.toCoins(): CoinModel {
        return CoinModel(
            id,
            icon,
            marketCap,
            name,
            price,
            priceChange1d,
            rank,
            symbol
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


    fun News.toNewsDetail(): NewsDetailModel {
        return NewsDetailModel(
            description = description,
            id = id,
            imgURL = imgURL,
            link = link,
            relatedCoins = relatedCoins,
            shareURL = shareURL,
            source = source,
            sourceLink = sourceLink,
            title = title
        )
    }



}