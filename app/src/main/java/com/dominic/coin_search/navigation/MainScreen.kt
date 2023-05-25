package com.dominic.coin_search.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.TopBar
import com.dominic.coin_search.ui.theme.Black800
import com.dominic.coin_search.ui.theme.Black920

@ExperimentalMaterialApi
@Composable
fun MainScreen(
    navViewModel: NavViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val (currency, onUpdatedCurrency) = rememberSaveable { (mutableStateOf<String?>(null)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val (dialogStateVisible, onDialogToggle) = rememberSaveable { mutableStateOf(false) }
    val (searchBarVisible, onSearchIconToggle) = rememberSaveable { mutableStateOf(false) }
    val navState by navViewModel.state.collectAsStateWithLifecycle()
    bottomBarState.value =
        run {

            navBackStackEntry?.destination?.route != Screens.CoinDetailScreen.route + "/{coinId}" &&
            navBackStackEntry?.destination?.route != Screens.SignInScreen.route &&
            navBackStackEntry?.destination?.route != Screens.SignUpScreen.route &&
            navBackStackEntry?.destination?.route != Screens.EmailAuthScreen.route &&
            navBackStackEntry?.destination?.route != Screens.IntroSliderScreen.route &&
            navBackStackEntry?.destination?.route != "${Screens.CompanyInfoScreen.route}/{symbol}"
        }

    Scaffold(topBar = {
        TopAppBar(
            route = navBackStackEntry?.destination?.route,
            currency = currency,
            onCurrencyDialogClick = {
                onDialogToggle(!dialogStateVisible)
            },
            onSearchIconToggle = {
                onSearchIconToggle(!searchBarVisible)
            })


    },
        bottomBar = {
            BottomBar(
                navController = navController,
                bottomBarVisibility = bottomBarState.value
            )
        }, content = { innerPadding ->
            navState.navigationStartingDestination?.let {
                NavGraph(
                    paddingValues = innerPadding,
                    navController = navController,
                    onUpdatedCurrency = onUpdatedCurrency,
                    searchBarVisible = searchBarVisible,
                    dialogStateVisible = dialogStateVisible,
                    startingDestination = it,
                    onDialogToggle = {
                        onDialogToggle(!dialogStateVisible)
                    },
                    onSearchIconToggle = {
                        onSearchIconToggle(!searchBarVisible)
                    })
            }
        })
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    route: String?,
    currency: String?,
    onCurrencyDialogClick: () -> Unit,
    onSearchIconToggle: () -> Unit) {
    when (route) {
        Screens.CoinsNews.route -> {
            TopBar(
                modifier = modifier,
                allowSearchField = false,
                currencyValue = null,
            )
        }

        Screens.CoinsScreen.route -> {
            TopBar(
                modifier = modifier,
                currencyValue = currency,
                allowSearchField = currency != null,
                onCurrencyDialogClick = onCurrencyDialogClick, onSearchClick = onSearchIconToggle)
        }

        Screens.FavoriteListScreen.route -> {
            TopBar(
                modifier = modifier,
                currencyValue = null,
                onSearchClick = onSearchIconToggle)
        }

        Screens.StockMarketScreen.route -> {
            TopBar(modifier = modifier,
                allowSearchField = false,
                currencyValue = null,)
        }


    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    bottomBarVisibility: Boolean
) {
    val screens = listOf(
        Screens.CoinsScreen,
        Screens.FavoriteListScreen,
        Screens.CoinsNews,
        Screens.StockMarketScreen
    )



    AnimatedVisibility(
        visible = bottomBarVisibility,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        Column {

            Divider(color = Black800, modifier = Modifier.fillMaxWidth(), thickness = 0.9.dp)

            BottomNavigation(
                backgroundColor = Black920,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                screens.forEach { screen ->

                    BottomNavigationItem(
                        label = {
                            Text(
                                text = screen.title!!,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1)
                        },
                        icon = {
                            when (screen.icon) {
                                is Int -> {
                                    Icon(
                                        painter = painterResource(screen.icon),
                                        contentDescription = null)
                                }

                                is ImageVector -> {
                                    Icon(imageVector = screen.icon, contentDescription = null)
                                }
                            }
                        },

                        selected = currentRoute == screen.route,

                        onClick = {
                            navController.navigateScreen(screen.route)
                        },

                        alwaysShowLabel = false,
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White.copy(alpha = ContentAlpha.disabled)
                    )
                }
            }
        }
    }

}

