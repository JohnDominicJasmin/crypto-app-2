package com.dominic.coin_search.feature_coins.domain.models

import com.dominic.coin_search.feature_coins.data.dto.Team

data class CoinInformationModel(
    val coinId:String,
    val name:String,
    val description:String? = null,
    val symbol:String,
    val rank:Int,
    val team:List<Team>? = null,
    val isActive:Boolean,
    val tags:List<String>? = null
)
