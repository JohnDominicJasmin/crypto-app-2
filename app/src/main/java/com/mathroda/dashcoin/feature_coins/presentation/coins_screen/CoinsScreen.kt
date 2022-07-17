package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.CoinsItem
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.SearchBar
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.TopBar
import com.mathroda.dashcoin.navigation.Screens
import com.mathroda.dashcoin.ui.theme.CustomGreen
import com.mathroda.dashcoin.ui.theme.DarkGray

@Composable
fun CoinsScreen(
    modifier: Modifier = Modifier,
    coinsViewModel: CoinsViewModel = hiltViewModel(),
    navController: NavController?
) {

    val coinsState by coinsViewModel.state.collectAsState()
    val context = LocalContext.current


    Box(
        modifier = modifier
            .background(DarkGray)
            .fillMaxSize()
    ) {
        Column {
            TopBar(title = "Live Prices")
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth(),
                searchQuery = coinsState.searchQuery,
                onValueChange = {
                    coinsViewModel.onEvent(event = CoinsEvent.EnteredSearchQuery(it))
                }
            )

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = coinsState.isRefreshing),
                onRefresh = { coinsViewModel.onEvent(event = CoinsEvent.RefreshCoins) }) {

                LazyColumn {

                    itemsIndexed(items = coinsState.coinModels.filter {
                        it.name.contains(coinsState.searchQuery.trim(), ignoreCase = true) ||
                        it.id.contains(coinsState.searchQuery.trim(), ignoreCase = true) ||
                        it.symbol.contains(coinsState.searchQuery.trim(), ignoreCase = true)
                    }, key = {_,item -> item.id}){ index: Int, coinModel: CoinModel->

                    CoinsItem(
                            context = context,
                            coinModel = coinModel,
                            chartModel = coinsState.chartModels.takeIf{it.isNotEmpty() && it.size > index }?.get(index),
                            onItemClick = {
                                navController?.navigate(Screens.CoinDetailScreen.route + "/${coinModel.id}")
                            }
                        )
                    }
                }
            }

        }


        if (coinsState.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center),
                color = CustomGreen
            )
        }

        if(coinsState.errorMessage.isNotEmpty()) {
            Text(
                text = coinsState.errorMessage,
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
