package com.dominic.coin_search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.SignInScreen
import com.dominic.coin_search.feature_authentication.presentation.authentication_sign_up.SignUpScreen
import com.dominic.coin_search.feature_coins.presentation.coin_detail.CoinDetailScreen
import com.dominic.coin_search.feature_coins.presentation.coins_news.NewsScreen
import com.dominic.coin_search.feature_coins.presentation.coins_screen.CoinsScreen
import com.dominic.coin_search.feature_favorites.presentation.favorites_screen.FavoriteListScreen
import com.dominic.coin_search.feature_stock_market.presentation.company_info.CompanyInfoScreen
import com.dominic.coin_search.feature_stock_market.presentation.company_listings.CompanyListingsScreen
import com.dominic.coin_search.feature_authentication.presentation.authentication_email.EmailAuthScreen
import com.dominic.coin_search.feature_intro_slider_screen.presentation.IntroSliderScreen

@ExperimentalMaterialApi
@Composable
fun NavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController,
    onUpdatedCurrency: (String?) -> Unit,
    searchBarVisible: Boolean = false,
    dialogStateVisible: Boolean = false,
    startingDestination: String,
    onDialogToggle: () -> Unit,
    onSearchIconToggle: () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = startingDestination
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

        composable(route = "${Screens.CompanyInfoScreen.route}/{symbol}",
            arguments = listOf(navArgument("symbol"){ type = NavType.StringType }
        )) {
            CompanyInfoScreen()
        }

        composable(route = Screens.StockMarketScreen.route) {
            CompanyListingsScreen(navController = navController)
        }

        composable(route = Screens.SignInScreen.route){
            SignInScreen(paddingValues = paddingValues, navController = navController)
        }

        composable(route = Screens.SignUpScreen.route){
            SignUpScreen(paddingValues = paddingValues, navController = navController)
        }
        composable(route = Screens.EmailAuthScreen.route){
            EmailAuthScreen(paddingValues = paddingValues, navController = navController)
        }
        composable(route = Screens.IntroSliderScreen.route){
            IntroSliderScreen(paddingValues = paddingValues, navController = navController)
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

fun NavController.navigateScreenInclusively(
    destination: String,
    popUpToDestination: String) {
    navigate(destination) {
        popUpTo(popUpToDestination) {
            inclusive = true
        }
        launchSingleTop = true
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