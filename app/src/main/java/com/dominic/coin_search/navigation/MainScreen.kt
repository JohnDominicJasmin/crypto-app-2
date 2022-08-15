package com.dominic.coin_search.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dominic.coin_search.ui.theme.Black800
import com.dominic.coin_search.ui.theme.Black920

@ExperimentalMaterialApi
@Composable
fun MainScreen(
) {
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    bottomBarState.value = navBackStackEntry?.destination?.route != Screens.CoinDetailScreen.route + "/{coinId}"

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                state = bottomBarState
            )
        }, content = {  innerPadding ->
            BottomNavGraph(paddingValues = innerPadding,navController = navController)
        })
}

@Composable
fun BottomBar(
    navController: NavHostController,
    state: MutableState<Boolean>
) {
    val screens = listOf(
        Screens.CoinsScreen,
        Screens.FavoriteListScreen,
        Screens.CoinsNews
    )



        AnimatedVisibility(
            visible = state.value,
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
                            Text(text = screen.title!!)
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

