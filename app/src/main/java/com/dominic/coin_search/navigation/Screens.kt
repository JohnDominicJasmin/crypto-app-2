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
        icon = R.drawable.save_2_svgrepo_com
    )

    object CoinsNews: Screens(
        route = "coins_news",
        title = "News",
        icon = R.drawable.news_svgrepo_com
    )

    object StockMarketScreen: Screens(
        route = "stock_market_screen",
        title = "Stock Market",
        icon = R.drawable.stock_market_svgrepo_com
    )

    object CompanyInfoScreen: Screens(
        route = "company_info_screen",
        title = "Company Info",
    )

    object SignInScreen: Screens(
        route = "sign_in_screen",
    )

    object SignUpScreen: Screens(
        route = "sign_up_screen",
    )

    object EmailAuthScreen: Screens(
        route = "email_auth_screen",
    )

    object CoinDetailScreen: Screens(route = "coin_detail_screen")






}
