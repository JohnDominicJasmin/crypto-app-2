package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.CoinsUiEvent
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.CoinsViewModel
import com.mathroda.dashcoin.navigation.Screens
import com.mathroda.dashcoin.ui.theme.CustomGreen
import com.mathroda.dashcoin.ui.theme.DarkGray
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach

@Composable
fun CoinScreen(
    viewModel: CoinsViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.state
    val searchCoin = remember { mutableStateOf(TextFieldValue(""))}
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { coinsUiEvent ->
            when(coinsUiEvent){
                is CoinsUiEvent.ShowNoInternetScreen -> {
                    //todo: show no internet screen here
                }

                is CoinsUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, coinsUiEvent.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }



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
                state = searchCoin
            )
            val isBeingSearched = searchCoin.value.text
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
                onRefresh = { viewModel.refresh() }) {

                LazyColumn {
                    items(items = state.coinModels.filter {
                        it.name.contains(isBeingSearched, ignoreCase = true) ||
                        it.id.contains(isBeingSearched, ignoreCase = true) ||
                        it.symbol.contains(isBeingSearched, ignoreCase = true)
                    }, key = {it.id}) { coins ->
                        CoinsItem(
                            coinModel = coins,
                            onItemClick = {
                                navController.navigate(Screens.CoinDetailScreen.route + "/${coins.id}")
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
/*        //todo: investigate this one
            Text(
                text = "ERROR SAMPLE",
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )*/
    }

}
