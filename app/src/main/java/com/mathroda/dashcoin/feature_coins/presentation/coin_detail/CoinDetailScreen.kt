package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mathroda.dashcoin.core.util.ConnectionStatus
import com.mathroda.dashcoin.core.util.millisToDate
import com.mathroda.dashcoin.core.util.toFormattedPrice

import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components.*
import com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen.FavoriteListEvent
import com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen.FavoriteListUiEvent
import com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen.FavoriteListViewModel
import com.mathroda.dashcoin.feature_no_internet.presentation.NoInternetScreen
import com.mathroda.dashcoin.navigation.Screens
import com.mathroda.dashcoin.navigation.navigateScreen
import com.mathroda.dashcoin.ui.theme.DarkGray
import com.mathroda.dashcoin.ui.theme.Green800
import com.mathroda.dashcoin.ui.theme.LighterGray
import com.mathroda.dashcoin.ui.theme.Twitter
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun CoinDetailScreen(
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    favoriteListViewModel: FavoriteListViewModel = hiltViewModel(),
    navController: NavController?
) {

    val coinState by coinDetailViewModel.state.collectAsState()

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val hasSelectedTimeSpan = remember(coinState.chartModel) {
        coinState.chartModel != null
    }


    LaunchedEffect(true) {

        favoriteListViewModel.eventFlow.collectLatest { savedListEvent ->
            when (savedListEvent) {
                is FavoriteListUiEvent.ShowSnackbar -> {

                    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = savedListEvent.message,
                        actionLabel = savedListEvent.buttonAction
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            if (savedListEvent.buttonAction == "See list") {
                                navController?.navigateScreen(Screens.FavoriteListScreen.route)
                                return@collectLatest
                            }

                            favoriteListViewModel.onEvent(event = FavoriteListEvent.RestoreDeletedCoin)
                            coinDetailViewModel.onEvent(event = CoinDetailEvent.ToggleFavoriteCoin)
                        }
                        else -> {}
                    }
                }
            }
        }
    }



    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState
    ) {

        val isDraggingChart = coinState.chartDate.isNotEmpty() && coinState.chartPrice.isNotEmpty()
        Box(
            modifier = Modifier
                .background(DarkGray)
                .fillMaxSize()) {

            coinState.coinDetailModel?.let { coin ->
                LazyColumn(
                    userScrollEnabled = !isDraggingChart,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    item {

                        TopBarCoinDetail(
                            coinModel = coin,
                            modifier = Modifier.padding(horizontal = 12.dp),
                            backButtonOnClick = { navController?.popBackStack() },
                            isFavorite = coinState.isFavorite,
                            favoriteButtonOnClick = {
                                if (!coinState.isFavorite) {
                                    favoriteListViewModel.onEvent(FavoriteListEvent.AddCoin(coin))
                                } else {
                                    favoriteListViewModel.onEvent(FavoriteListEvent.DeleteCoin(coin))
                                }
                                coinDetailViewModel.onEvent(event = CoinDetailEvent.ToggleFavoriteCoin)

                            }
                        )

                        CoinDetailSection(
                            modifier = Modifier.padding(horizontal = 12.dp),
                            coinModel = coin,
                            currencySymbol = coinState.currencySymbol,
                            chartDate =  coinState.chartDate,
                            chartPrice = coinState.chartPrice,
                        )

                        if(!hasSelectedTimeSpan) {

                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .requiredHeight(100.dp)) {
                                CircularProgressIndicator(
                                    color = Green800,
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center)
                                        .size(32.5.dp))
                            }
                        }

                        AnimatedVisibility(
                            visible = hasSelectedTimeSpan,
                            enter = fadeIn(
                                initialAlpha = 0.6f,
                                animationSpec = tween(
                                    durationMillis = 300,
                                    delayMillis = 300
                                )
                            )) {
                            Column {
                                CoinDetailChart(
                                    chartModel = coinState.chartModel!!,
                                    chartPeriod = coinState.coinChartPeriod,
                                    priceChange = coin.priceChange1w,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(300.dp),
                                    onChangePriceValue = { yValue ->
                                        coinDetailViewModel.onEvent(
                                            event = CoinDetailEvent.ChangeYAxisValue(yValue))
                                    },
                                    onChangeDateValue = { xValue ->
                                        coinDetailViewModel.onEvent(
                                            event = CoinDetailEvent.ChangeXAxisValue(xValue))
                                    }
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
                                    })
                            }
                        }






                        CoinInformation(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp)
                                .background(
                                    color = LighterGray,
                                    shape = RoundedCornerShape(25.dp)
                                )
                                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                            rank = "${coin.rank}",
                            volume = coin.volume.toFormattedPrice(),
                            marketCap = coin.marketCap.toFormattedPrice(),
                            availableSupply = "${coin.availableSupply.toFormattedPrice()} ${coin.symbol}",
                            totalSupply = "${ coin.totalSupply.toFormattedPrice()} ${coin.symbol}"
                        )

                        val uriHandler = LocalUriHandler.current
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)) {
                            LinkButton(
                                title = "Twitter",
                                modifier = Modifier
                                    .padding(start = 20.dp, bottom = 20.dp, top = 20.dp)
                                    .clip(RoundedCornerShape(35.dp))
                                    .height(45.dp)
                                    .background(Twitter)
                                    .weight(1f)
                                    .clickable {
                                        runCatching {
                                            uriHandler.openUri(coin.twitterUrl!!)
                                        }.onFailure {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Twitter is not available",
                                                    Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                            )

                            LinkButton(
                                title = "Website",
                                modifier = Modifier
                                    .padding(start = 20.dp, bottom = 20.dp, top = 20.dp)
                                    .clip(RoundedCornerShape(35.dp))
                                    .height(45.dp)
                                    .background(LighterGray)
                                    .weight(1f)
                                    .clickable {
                                        runCatching {
                                            uriHandler.openUri(coin.websiteUrl!!)
                                        }.onFailure {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Website is not available",
                                                    Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    }
                            )
                        }
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
                    if (ConnectionStatus.hasInternetConnection(context)) {
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



