package com.mathroda.dashcoin.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components.CoinDetailScreen
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.CoinScreen
import com.mathroda.dashcoin.feature_coins.presentation.coins_news.components.NewsScreen
import com.mathroda.dashcoin.feature_no_internet.presentation.components.NoInternetScreen
import com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen.SavedListScreen

@ExperimentalMaterialApi
@Composable
fun BottomNavGraph(navController: NavHostController) {

    //todo: convert this to navigate lambda
    NavHost(
        navController = navController,
        startDestination = Screens.CoinsScreen.route
    ) {

        composable(route = Screens.CoinsScreen.route){
            CoinScreen(navController = navController)
        }

        composable(route = Screens.SavedWatchList.route){
            SavedListScreen(navController = navController)
        }

        composable(route = Screens.CoinsNews.route){
            NewsScreen(navController = navController)
        }

        composable(route = Screens.CoinDetailScreen.route + "/{coinId}"){
            CoinDetailScreen(navController = navController)
        }

        composable(route = Screens.NoInternetScreen.route){
            NoInternetScreen {
                navController.popBackStack()
            }
        }
    }
}