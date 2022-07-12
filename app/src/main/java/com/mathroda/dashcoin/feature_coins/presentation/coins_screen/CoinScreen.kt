package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mathroda.dashcoin.core.util.ConnectionStatus
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.CoinsItem
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.SearchBar
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.TopBar
import com.mathroda.dashcoin.feature_no_internet.presentation.NoInternetScreen
import com.mathroda.dashcoin.navigation.Screens
import com.mathroda.dashcoin.ui.theme.CustomGreen
import com.mathroda.dashcoin.ui.theme.DarkGray

@Composable
fun CoinScreen(
    viewModel: CoinsViewModel = hiltViewModel(),
    navController: NavController?
) {

    val state = viewModel.state
    val context = LocalContext.current



    Box(
        modifier = Modifier
            .background(DarkGray)
            .fillMaxSize()
    ) {
        Column {
            TopBar(title = "Live Prices")
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth(),
                searchQuery = state.searchQuery,
                onValueChange = {
                    viewModel.onEvent(event = CoinsEvent.EnteredSearchQuery(it))
                }
            )

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
                onRefresh = { viewModel.onEvent(event = CoinsEvent.RefreshCoins) }) {

                LazyColumn {
                    items(items = state.coinModels.filter {
                        it.name.contains(state.searchQuery, ignoreCase = true) ||
                        it.id.contains(state.searchQuery, ignoreCase = true) ||
                        it.symbol.contains(state.searchQuery, ignoreCase = true)
                    }, key = {it.id}) { coins ->
                        CoinsItem(
                            coinModel = coins,
                            onItemClick = {
                                navController?.navigate(Screens.CoinDetailScreen.route + "/${coins.id}")
                            }
                        )
                    }
                }
            }

        }


        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center),
                color = CustomGreen
            )
        }


        if(!state.hasInternet){
            NoInternetScreen(onTryButtonClick = {
                if(ConnectionStatus.hasInternetConnection(context)){
                    viewModel.onEvent(event = CoinsEvent.CloseNoInternetDisplay)
                }
            })
        }

        if(state.errorMessage.isNotEmpty()) {
            Text(
                text = state.errorMessage,
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
