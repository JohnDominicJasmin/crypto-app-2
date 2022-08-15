package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dominic.coin_search.feature_coins.presentation.coin_detail.CoinDetailEvent
import com.dominic.coin_search.navigation.Screens
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.TopBar
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.FavoriteAddButton
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.FavoriteToggleButton
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.SavedCoinItem
import com.dominic.coin_search.navigation.navigateScreen
import com.dominic.coin_search.ui.theme.Black450
import com.dominic.coin_search.ui.theme.DarkGray
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun FavoriteListScreen(
    innerPaddingValues: PaddingValues,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController?
) {
    val favoriteState by favoritesViewModel.state.collectAsState()
    val context = LocalContext.current
    val (isCoinSelected, onCoinSelected) = rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(true) {

        favoritesViewModel.eventFlow.collectLatest { savedListEvent ->
            when (savedListEvent) {
                is FavoriteListUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, savedListEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




    Scaffold(
        topBar = {
        TopBar(
            currencyValue = null,
            modifier = Modifier
                .height(55.dp)
                .padding(bottom = 5.dp, top = 14.dp, start = 15.dp, end = 5.dp)
                .fillMaxWidth(),
            onSearchClick = {

            })
    }) {


        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {





            LazyColumn(
                modifier = Modifier
                    .background(DarkGray)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {

                item {
                    FavoriteToggleButton(
                        modifier = Modifier.padding(top = 10.dp, bottom = 7.dp),
                        isCoinSelected = isCoinSelected,
                        onCoinsButtonClick = {onCoinSelected(it)})
                }
                if(isCoinSelected) {
                    items(favoriteState.coins, key = { it.id }) { coin ->
                        SavedCoinItem(
                            modifier = Modifier.animateItemPlacement(
                                tween(durationMillis = 500)
                            ),
                            coin = coin,
                            onItemClick = {
                                navController?.navigate(Screens.CoinDetailScreen.route + "/${coin.id}")
                            },
                            onDeleteClick = {
                                favoritesViewModel.onEvent(FavoriteListEvent.DeleteCoin(coin))
                            }
                        )
                    }
                }else{
                    item{

                    }
                }



            }




            if (favoriteState.coins.isEmpty()) {
                Column(modifier = Modifier.align(Alignment.Center), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No saved coins to display",
                        color = Black450,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)

                    )
                    FavoriteAddButton(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        onClick = {
                            navController?.navigateScreen(Screens.CoinsScreen.route)
                        })
                }
            }


        }
    }
}

