package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dominic.coin_search.navigation.Screens
import com.dominic.coin_search.feature_coins.presentation.coin_detail.CoinDetailViewModel
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.TopBar
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.MarketStatusBar
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.WatchlistItem
import com.dominic.coin_search.ui.theme.Green800
import com.dominic.coin_search.ui.theme.DarkGray
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun FavoriteListScreen(
    modifier: Modifier = Modifier,
    favoriteListViewModel: FavoriteListViewModel = hiltViewModel(),
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    navController: NavController?
) {
    val watchListState = favoriteListViewModel.state
    val coinDetailState by coinDetailViewModel.state.collectAsState()




    LaunchedEffect(true) {
        favoriteListViewModel.eventFlow.collectLatest { savedListEvent ->

            when (savedListEvent) {
                is FavoriteListUiEvent.ShowSnackbar -> {
                    //todo add snackbar
                }
            }
        }
    }


    Scaffold(topBar = {
        TopBar(
            currencyValue = null,
            modifier = Modifier
                .height(55.dp)
                .padding(bottom = 5.dp, top = 14.dp, start = 15.dp, end = 5.dp)
                .fillMaxWidth(),
            onCurrencyClick = {
            }, onSearchClick = {

            })
    }) {


        Box(
            modifier = modifier
                .background(DarkGray)
                .fillMaxSize()
        ) {

            Column {
                coinDetailState.coinDetailModel?.let { status ->
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        item {
                            MarketStatusBar(
                                marketStatus1h = status.priceChange1h,
                                marketStatus1d = status.priceChange1d,
                                marketStatus1w = status.priceChange1w,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 18.dp, bottom = 12.dp)
                            )
                        }
                    }
                }


                LazyColumn {
                    items(watchListState.coins) { coin ->
                        WatchlistItem(
                            icon = coin.icon,
                            coinName = coin.name,
                            symbol = coin.symbol,
                            rank = coin.rank.toString(),
                            onClick = {
                                navController?.navigate(Screens.CoinDetailScreen.route + "/${coin.id}") {
                                    popUpTo(Screens.FavoriteListScreen.route) {
                                        this.inclusive = true
                                    }
                                }

                            }
                        )
                    }
                }

            }
            if (coinDetailState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Green800
                )
            }

            if (watchListState.coins.isEmpty()) {
                Text(
                    text = "No saved coins to display",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)

                )
            }




            if (coinDetailState.errorMessage.isNotEmpty()) {
                Text(
                    text = coinDetailState.errorMessage,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }

    }
}