package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.core.util.ConnectionStatus
import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.*
import com.mathroda.dashcoin.feature_no_internet.presentation.NoInternetScreen
import com.mathroda.dashcoin.navigation.Screens
import com.mathroda.dashcoin.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun CoinsScreen(
    modifier: Modifier = Modifier,
    coinsViewModel: CoinsViewModel = hiltViewModel(),
    navController: NavController?
) {

    val coinsState by coinsViewModel.state.collectAsState()
    val context = LocalContext.current
    val (dialogStateVisible, onDialogToggle) = remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val scrollingForward by remember(true) { derivedStateOf { listState.firstVisibleItemScrollOffset > 0 } }

    val isScrollingUp = listState.isScrollingUp() && scrollingForward

    val coinList = coinsState.coinModels





    Scaffold(topBar = {

        val currency = coinsState.coinCurrencyPreference.currency

        AnimatedVisibility(
            visible = currency != null && coinsState.tickerVisible,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 150, easing = FastOutLinearInEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing))) {

            Column {
                TopBar(
                    currencyValue = currency,
                    modifier = Modifier
                        .height(55.dp)
                        .padding(bottom = 5.dp, top = 14.dp, start = 15.dp, end = 5.dp)
                        .fillMaxWidth(),
                    onCurrencyClick = {
                        onDialogToggle(!dialogStateVisible)
                    }, onSearchClick = {

                    })

                MarqueeText(
                    textModifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Black450)) {
                            append("Number of Cryptos: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.White)) {
                            append("${coinsState.globalMarket.cryptocurrenciesNumber}        |        ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Black450)) {
                            append("Market Cap: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.White)) {
                            append("$${(coinsState.globalMarket.marketCapUsd.formatToShortNumber())}        |        ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Black450)) {
                            append("24h Vol: ")
                        }

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.White)) {
                            append("$${(coinsState.globalMarket.volume24hUsd.formatToShortNumber())}        |        ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Black450)) {
                            append("BTC Dominance: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.White)) {
                            append("${coinsState.globalMarket.bitcoinDominancePercentage}%        |        ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Black450)) {
                            append("Market Cap All-Time High: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.White)) {
                            append("${coinsState.globalMarket.marketCapAllTimeHigh.formatToShortNumber()}        |        ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Black450)) {
                            append("Volume All-Time High(24): ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.White)) {
                            append(coinsState.globalMarket.volume24hAllTimeHigh.formatToShortNumber())
                        }

                    },
                    gradientEdgeColor = DarkGray,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )

            }
        }

    }, floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {

            AnimatedVisibility(
                visible = isScrollingUp,
                enter = fadeIn(animationSpec = tween(durationMillis = 3000,delayMillis = 50,), initialAlpha = 1f),
                exit = fadeOut(
                    animationSpec = tween()
                )) {


            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.scrollToItem(index = 0)
                    }
                },
                modifier = Modifier
                    .offset(x = 0.dp, y = (-70).dp)
                    .size(50.dp),
                backgroundColor = Color.Black,
                contentColor = GREEN600
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back_to_top),
                    contentDescription = "Back to top button ",
                    modifier = Modifier.padding(all = 15.3.dp), )
            }
        }

    }) {


        Box(
            modifier = modifier
                .background(DarkGray)
                .fillMaxSize()) {

            Column {

                AnimatedVisibility(visible = dialogStateVisible) {

                    CoinCurrencyScreen(
                        currencies = coinsState.currencies,
                        onDismissRequest = { selectedCurrency: CoinCurrencyPreference? ->
                            onDialogToggle(!dialogStateVisible)

                            coinsViewModel.onEvent(
                                event = CoinsEvent.SelectCurrency(
                                    coinCurrencyPreference = selectedCurrency
                                                             ?: return@CoinCurrencyScreen))


                        })

                }


                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = coinsState.isRefreshing),
                    onRefresh = { coinsViewModel.onEvent(event = CoinsEvent.RefreshCoins(coinsState.coinCurrencyPreference)) }) {

                    if (coinsState.isItemsRendered) {

                        LazyColumn(state = listState){//todo: this filtering causes lag try using derivedStateOf
                            items(count = coinList.size, key = {coinList[it].id}){ index ->
                                val coinModel = coinList[index]
                                CoinsItem(
                                    isLoading = coinsState.isLoading,
                                    currencySymbol = coinsState.coinCurrencyPreference.currencySymbol
                                                     ?: "N/A",
                                    coinModel = coinModel,
                                    chartModel = coinsState.chart.takeIf { it.isNotEmpty() && it.size > index }
                                        ?.get(index),
                                    onItemClick = {
                                        navController?.navigate(Screens.CoinDetailScreen.route + "/${coinModel.id}")
                                    }
                                )
                            }
                        }
                    }

                }
            }


            if (coinsState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = CustomGreen
                )
            }


            if (coinList.isEmpty() && !coinsState.hasInternet) {
                NoInternetScreen(onTryButtonClick = {
                    if (ConnectionStatus.hasInternetConnection(context)) {
                        coinsViewModel.onEvent(event = CoinsEvent.CloseNoInternetDisplay)
                        coinsViewModel.onEvent(event = CoinsEvent.RefreshCoins(coinsState.coinCurrencyPreference))
                    }
                })
            }

            if (coinsState.errorMessage.isNotEmpty()) {
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
}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) } //todo add +1 to avoid very first index
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}
fun Long.formatToShortNumber(): String {
    return when {
        this >= 1000000000000 -> String.format("%.2f T", this / 1000000000000.0)
        this >= 1000000000 -> String.format("%.2f B", this / 1000000000.0)
        this >= 1000000 -> String.format("%.2f M", this / 1000000.0)
        this >= 1000 -> String.format("%.2f K", this / 1000.0)
        else -> this.toString()
    }
}


