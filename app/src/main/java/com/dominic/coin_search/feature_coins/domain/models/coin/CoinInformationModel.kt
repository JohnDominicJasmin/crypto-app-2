package com.dominic.coin_search.feature_coins.domain.models.coin

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.dominic.coin_search.feature_coins.data.dto.coin_information.Team

@Immutable
@Stable
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
