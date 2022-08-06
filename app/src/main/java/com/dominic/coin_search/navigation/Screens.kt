package com.dominic.coin_search.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import com.dominic.coin_search.R

sealed class Screens(
    val route: String,
    val title: String? = null,
    val icon: Any? = null) {

    object CoinsScreen: Screens(
        route = "coins_screen",
        title = "Home",
        icon = Icons.Default.Home
    )

    object FavoriteListScreen: Screens(
        route = "favorite_list_screen",
        title = "Favorites",
        icon = R.drawable.ic_baseline_bookmark_filled
    )

    object CoinsNews: Screens(
        route = "coins_news",
        title = "News",
        icon = Icons.Default.List
    )
    object CoinDetailScreen: Screens("coin_detail_screen")





}
