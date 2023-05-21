package com.dominic.coin_search.feature_coins.presentation.coin_detail

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dominic.coin_search.core.util.ConnectionStatus
import com.dominic.coin_search.core.util.ConnectionStatus.hasInternetConnection
import com.dominic.coin_search.core.util.Formatters.toFormattedPrice
import com.dominic.coin_search.feature_coins.presentation.coin_detail.components.*
import com.dominic.coin_search.feature_favorites.presentation.favorites_screen.FavoritesEvent
import com.dominic.coin_search.feature_favorites.presentation.favorites_screen.FavoritesUiEvent
import com.dominic.coin_search.feature_favorites.presentation.favorites_screen.FavoritesViewModel
import com.dominic.coin_search.feature_no_internet.presentation.NoInternetScreen
import com.dominic.coin_search.ui.theme.Black450
import com.dominic.coin_search.ui.theme.Black920
import com.dominic.coin_search.ui.theme.DarkGray
import com.dominic.coin_search.ui.theme.Green800
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CoinDetailScreen(
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController?
) {

    val coinState by coinDetailViewModel.state.collectAsState()

    val context = LocalContext.current

    val onOpenUrl = { url: String?, name: String ->
        runCatching {
            openBrowser(context, url!!)
        }.onFailure {
            Toast.makeText(context, "$name is not available", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(true) {

        favoritesViewModel.eventFlow.collectLatest { savedListEvent ->
            when (savedListEvent) {
                is FavoritesUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, savedListEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    Scaffold(
        modifier = Modifier,
    ) { paddingValues ->

        val isDraggingChart = coinState.chartDate.isNotEmpty() && coinState.chartPrice != 0.0

        Box(
            modifier = Modifier
                .background(DarkGray)
                .fillMaxSize()) {

            coinState.coinDetailModel?.let { coinDetail ->

                LazyColumn(
                    userScrollEnabled = !isDraggingChart,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {

                        TopBarCoinDetail(
                            coinModel = coinDetail,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            backButtonOnClick = { navController?.popBackStack() },
                            isFavorite = coinState.isFavorite,
                            favoriteButtonOnClick = {
                                if (!coinState.isFavorite) {
                                    favoritesViewModel.onEvent(
                                        FavoritesEvent.AddCoin(
                                            coinDetail))
                                } else {
                                    favoritesViewModel.onEvent(
                                        FavoritesEvent.DeleteCoin(
                                            coinDetail))
                                }
                                coinDetailViewModel.onEvent(event = CoinDetailEvent.ToggleFavoriteCoin)

                            }
                        )

                        CoinDetailSection(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            coinModel = coinDetail,
                            chartDate = coinState.chartDate,
                            chartPrice = coinState.chartPrice * coinState.currencyExchange,
                            currencySymbol = coinState.coinCurrencyPreference.currencySymbol ?: "$",
                        )

                        AnimatedVisibility(
                            visible = coinState.chartModel != null,
                            enter = fadeIn(
                                initialAlpha = 0.6f,
                                animationSpec = tween(
                                    durationMillis = 1000,
                                    delayMillis = 100
                                )
                            )) {
                            Column {
                                CoinDetailChart(
                                    chartModel = coinState.chartModel!!,
                                    chartPeriod = coinState.coinChartPeriod,
                                    priceChange = coinDetail.priceChange1w,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(270.dp),
                                    coinDetailViewModel = coinDetailViewModel
                                )


                                TimeSpanPicker(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(vertical = 17.dp, horizontal = 1.5.dp),
                                    selectedTimeSpan = coinState.coinChartPeriod,
                                    onTimeSpanSelected = { selectedTimeSpan ->
                                        coinDetailViewModel.onEvent(
                                            event = CoinDetailEvent.SelectChartPeriod(
                                                period = selectedTimeSpan.value))
                                        coinDetailViewModel.onEvent(event = CoinDetailEvent.ClearChartDate)
                                        coinDetailViewModel.onEvent(event = CoinDetailEvent.ClearChartPrice)
                                    })
                            }
                        }


                        CoinInformation(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 12.dp)
                                .background(
                                    color = Black920,
                                    shape = RoundedCornerShape(18.dp))
                                .padding(horizontal = 15.dp, vertical = 10.dp),
                            rank = "${coinDetail.rank}",
                            volume = "$${coinDetail.volume.toFormattedPrice()}",
                            marketCap = "$${coinDetail.marketCap.toFormattedPrice()}",
                            availableSupply = "${coinDetail.availableSupply.toFormattedPrice()} ${coinDetail.symbol}",
                            totalSupply = "${coinDetail.totalSupply.toFormattedPrice()} ${coinDetail.symbol}"
                        )

                    }

                    item {
                        if (coinState.coinInformation != null) {
                            with(coinState.coinInformation!!) {

                                CoinTitleSection(
                                    coinInfo = this@with,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 15.dp, end = 15.dp, top = 12.dp))

                                CoinDescription(
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .padding(start = 15.dp, end = 15.dp, top = 8.dp),
                                    description = this@with.description)

                                CoinTagsSection(
                                    modifier = Modifier.padding(
                                        start = 15.dp, end = 15.dp, top = 15.dp),
                                    tags = Tags(this@with.tags))

                                CoinTeamMembers(
                                    modifier = Modifier.padding(
                                        start = 15.dp,
                                        end = 15.dp,
                                        top = 15.dp),
                                    coinInformationModel = this@with,
                                    context = context)
                            }
                            return@item
                        }

                        if (!coinState.isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 15.dp),
                                contentAlignment = Alignment.Center) {
                                Text(
                                    text = "No Coin Information Available",
                                    color = Black450,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center))
                            }
                        }
                    }


                    item {
                        CoinLinkButtons(
                            onOpenTwitterUrl = {
                                onOpenUrl(coinDetail.twitterUrl, "Twitter")
                            },
                            onOpenWebsiteUrl = {
                                onOpenUrl(coinDetail.websiteUrl, "Website")
                            })
                    }
                }

            }
            if (coinState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Green800
                )
            }


            if (!coinState.hasInternet) {
                NoInternetScreen(onTryButtonClick = {
                    if (context.hasInternetConnection()) {
                        coinDetailViewModel.onEvent(event = CoinDetailEvent.CloseNoInternetDisplay)
                        coinDetailViewModel.onEvent(event = CoinDetailEvent.LoadCoinDetail)
                    }
                })
            }

            if (coinState.errorMessage.isNotEmpty()) {
                Text(
                    text = coinState.errorMessage,
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

