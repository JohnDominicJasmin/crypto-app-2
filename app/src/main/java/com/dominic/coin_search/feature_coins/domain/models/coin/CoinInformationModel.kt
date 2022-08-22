package com.dominic.coin_search.feature_coins.domain.models.coin

import com.dominic.coin_search.feature_coins.data.dto.coin_information.Team

data class CoinInformationModel(
    val coinId:String = "",
    val name:String = "",
    val description:String = "",
    val symbol:String = "",
    val rank:Int = 0,
    val team:List<Team> = emptyList(),
    val isActive:Boolean = false,
    val tags:List<String> = emptyList()
)
