package com.mathroda.dashcoin.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mathroda.dashcoin.ui.theme.LighterGray
import com.mathroda.dashcoin.ui.theme.White800

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
            BottomNavGraph(modifier = Modifier.padding(innerPadding),navController = navController)
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
    ){
        BottomNavigation(
            backgroundColor = LighterGray,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            screens.forEach { screen ->

                BottomNavigationItem(
                    label = {
                        Text(text = screen.title!!)
                    },
                    icon = {
                        when(screen.icon){
                            is Int -> {
                                Icon(painter = painterResource(screen.icon) , contentDescription = null)
                            }
                            is ImageVector -> {
                                Icon(imageVector = screen.icon , contentDescription = null)
                            }
                        }
                    },

                    selected = currentRoute == screen.route,

                    onClick = {
                        navController.navigateScreen(screen.route)
                    },

                    alwaysShowLabel = false,
                    selectedContentColor = White800,
                    unselectedContentColor = White800.copy(alpha = ContentAlpha.disabled)
                )
            }
        }
    }


}

