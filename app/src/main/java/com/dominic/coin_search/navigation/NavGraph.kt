package com.dominic.coin_search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dominic.coin_search.feature_coins.presentation.coin_detail.CoinDetailScreen
import com.dominic.coin_search.feature_coins.presentation.coins_news.NewsScreen
import com.dominic.coin_search.feature_coins.presentation.coins_screen.CoinsScreen
import com.dominic.coin_search.feature_favorites.presentation.favorites_screen.FavoriteListScreen

@ExperimentalMaterialApi
@Composable
fun NavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController,
    onUpdatedCurrency: (String?) -> Unit,
    searchBarVisible: Boolean = false,
    dialogStateVisible: Boolean = false,
    onDialogToggle: () -> Unit,
    onSearchIconToggle: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = Screens.CoinsScreen.route
    ) {

        composable(route = Screens.CoinsScreen.route) {
            CoinsScreen(
                innerPaddingValues = paddingValues,
                navController = navController,
                onUpdatedCurrency = onUpdatedCurrency,
                searchBarVisible = searchBarVisible,
                dialogStateVisible = dialogStateVisible,
                onDialogToggle = onDialogToggle,
                onSearchIconToggle = onSearchIconToggle)
        }

        composable(route = Screens.FavoriteListScreen.route) {
            FavoriteListScreen(
                innerPaddingValues = paddingValues,
                navController = navController,
                searchBarVisible = searchBarVisible,
                onSearchIconToggle = onSearchIconToggle)
        }

        composable(route = Screens.CoinsNews.route) {
            NewsScreen(innerPaddingValues = paddingValues)
        }

        composable(route = Screens.CoinDetailScreen.route + "/{coinId}") {
            CoinDetailScreen(navController = navController)
        }


    }
}


fun NavController.navigateScreen(destination: String) {

    navigate(destination) {

        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }


}