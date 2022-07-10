package com.mathroda.dashcoin.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.CoinDetailScreen
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.CoinScreen
import com.mathroda.dashcoin.feature_coins.presentation.coins_news.NewsScreen
import com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen.FavoriteListScreen

@ExperimentalMaterialApi
@Composable
fun BottomNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screens.CoinsScreen.route
    ) {

        composable(route = Screens.CoinsScreen.route){
            CoinScreen(navController = navController)
        }

        composable(route = Screens.FavoriteListScreen.route){
            FavoriteListScreen(navController = navController)
        }

        composable(route = Screens.CoinsNews.route){
            NewsScreen(navController = navController)
        }

        composable(route = Screens.CoinDetailScreen.route + "/{coinId}"){
            CoinDetailScreen(navController = navController)
        }


    }
}


 fun NavController.navigateScreen(
    destination: String) {

    navigate(destination) {

        popUpTo(graph.findStartDestination().id){
            saveState = true
        }

        launchSingleTop = true
        restoreState =true
    }


}