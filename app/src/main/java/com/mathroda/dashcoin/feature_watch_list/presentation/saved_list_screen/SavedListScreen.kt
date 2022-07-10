package com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.CoinDetailUiEvent
import com.mathroda.dashcoin.navigation.Screens
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.CoinDetailViewModel
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.TopBar
import com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen.components.MarketStatusBar
import com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen.components.WatchlistItem
import com.mathroda.dashcoin.ui.theme.CustomGreen
import com.mathroda.dashcoin.ui.theme.DarkGray
import com.mathroda.dashcoin.ui.theme.LighterGray
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun SavedListScreen(
    savedListViewModel: SavedListViewModel = hiltViewModel(),
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    navController: NavController
) {
    val watchListState = savedListViewModel.state
    val marketState = coinDetailViewModel.state
    val context = LocalContext.current


    LaunchedEffect(key1 = true){
        coinDetailViewModel.eventFlow.collectLatest { coinDetailEvent ->
            when(coinDetailEvent){
                is CoinDetailUiEvent.ShowNoInternetScreen -> {
                    //todo: show no internet screen here
                }

                is CoinDetailUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, coinDetailEvent.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }










    Box(
        modifier = Modifier
            .background(DarkGray)
            .fillMaxSize()
            .padding(12.dp)
            .padding(bottom = 45.dp)
    ) {

        Column {
            TopBar(title = "Watch List")

            marketState.coinDetailModel?.let { status ->
                LazyColumn(modifier = Modifier.fillMaxWidth()){
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


            Divider(color = LighterGray, modifier = Modifier.padding(bottom = 10.dp))
            LazyColumn {
                items(watchListState.coin) { coin ->
                    WatchlistItem(
                        icon = coin.icon,
                        coinName = coin.name,
                        symbol = coin.symbol,
                        rank = coin.rank.toString(),
                        onClick = {
                            navController.navigate(Screens.CoinDetailScreen.route + "/${coin.id}")
                        }
                    )
                }
            }

        }
        if (marketState.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center),
                color = CustomGreen
            )
        }
        //todo: investigate this one

            Text(
                text = "ERROR SAMPLE",
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

}