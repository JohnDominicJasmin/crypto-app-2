package com.dominic.coin_search.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dominic.coin_search.feature_coins.presentation.coin_detail.CoinDetailScreen
import com.dominic.coin_search.feature_coins.presentation.coins_news.NewsScreen
import com.dominic.coin_search.feature_coins.presentation.coins_screen.CoinsScreen
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.FavoriteListScreen

@ExperimentalMaterialApi
@Composable
fun BottomNavGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screens.CoinsScreen.route
    ) {

        composable(route = Screens.CoinsScreen.route){
            CoinsScreen(modifier = modifier, navController = navController)
        }

        composable(route = Screens.FavoriteListScreen.route){
            FavoriteListScreen(modifier = modifier, navController = navController)
        }

        composable(route = Screens.CoinsNews.route){
            NewsScreen(modifier = modifier, navController = navController)
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