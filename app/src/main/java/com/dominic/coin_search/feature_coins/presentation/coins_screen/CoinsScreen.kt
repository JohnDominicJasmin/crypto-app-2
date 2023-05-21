package com.dominic.coin_search.feature_coins.presentation.coins_screen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.dominic.coin_search.R
import com.dominic.coin_search.core.util.ConnectionStatus
import com.dominic.coin_search.core.util.ConnectionStatus.hasInternetConnection
import com.dominic.coin_search.core.util.Formatters.formatToShortNumber
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinCurrencyPreference
import com.dominic.coin_search.feature_coins.presentation.coin_currency_screen.CoinCurrencyScreen
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.*
import com.dominic.coin_search.feature_no_internet.presentation.NoInternetScreen
import com.dominic.coin_search.navigation.Screens
import com.dominic.coin_search.ui.theme.*
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CoinsScreen(
    onUpdatedCurrency: (String?) -> Unit,
    innerPaddingValues: PaddingValues,
    coinsViewModel: CoinsViewModel = hiltViewModel(),
    navController: NavController?,
    searchBarVisible: Boolean = false,
    dialogStateVisible: Boolean = false,
    onDialogToggle: () -> Unit,
    onSearchIconToggle : () -> Unit,
) {

    val coinsState by coinsViewModel.state.collectAsState()
    val coinChart = coinsViewModel.coinChart
    val context = LocalContext.current


    val (searchQuery, onChangeValueSearch) = rememberSaveable { mutableStateOf("") }

    val coinList = coinsState.coins.coinModels

    val filteredCoins = remember(searchQuery, coinList) {
        coinList.filter {
            it.name.contains(searchQuery.trim(), ignoreCase = true) ||
            it.id.contains(searchQuery.trim(), ignoreCase = true) ||
            it.symbol.contains(searchQuery.trim(), ignoreCase = true)
        }
    }


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isScrolling by remember { derivedStateOf { listState.firstVisibleItemScrollOffset > 15 } }

    val userScrolling = listState.isScrollingUp() && isScrolling


    val currency = coinsState.coinCurrencyPreference.currency

    LaunchedEffect(key1 = currency){
        onUpdatedCurrency(currency)
    }

    Scaffold(topBar = {

            AnimatedVisibility(
                visible = !searchBarVisible && coinsState.tickerVisible,
                enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
                exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)) {


                AnimatedVisibility(
                    visible = !listState.isScrollInProgress,
                    enter = fadeIn(
                        initialAlpha = 0.4f
                    ), exit = fadeOut(
                        animationSpec = tween(durationMillis = 250)
                    )) {
                    MarqueeText(
                        modifier = Modifier.padding(top = 5.dp, bottom = 8.dp),
                        annotatedText = buildAnnotatedString {
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

                    )
                }
            }

    },
        floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {

            AnimatedVisibility(
                visible = userScrolling,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 3000, delayMillis = 50),
                    initialAlpha = 1f),
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
                    backgroundColor = GreenBlue600,
                    contentColor = Black920
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_to_top),
                        contentDescription = "Back to top button ",
                        modifier = Modifier.padding(all = 15.3.dp),
                    )
                }
            }

        }) {


        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize()
                .animateContentSize(animationSpec = tween(durationMillis = 70)),
            contentAlignment = Alignment.TopCenter) {

            Column {

                AnimatedVisibility(visible = dialogStateVisible) {

                    CoinCurrencyScreen(
                        coinFiat = coinsState.currencies,
                        onDismissRequest = { selectedCurrency: CoinCurrencyPreference? ->
                            onDialogToggle()
                            coinsViewModel.onEvent(event = CoinsEvent.RefreshCoins(coinsState.coinCurrencyPreference)).also{
                                coinsViewModel.onEvent(event = CoinsEvent.SelectCurrency(coinCurrencyPreference = selectedCurrency ?: return@CoinCurrencyScreen))

                            }

                        })

                }

                AnimatedVisibility(visible = searchBarVisible) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(top = 4.dp, bottom = 10.dp),
                            searchQuery = searchQuery,
                            onValueChange = { value ->
                                onChangeValueSearch(value)
                            },
                            hasFocusRequest = false,
                            hasTrailingIcon = true,
                            onTrailingIconClick = {
                                onSearchIconToggle()
                                onChangeValueSearch("")
                            }

                        )
                    }
                }

                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = coinsState.isRefreshing),
                    onRefresh = { coinsViewModel.onEvent(event = CoinsEvent.RefreshCoins(coinsState.coinCurrencyPreference)) }) {

                    if (coinsState.isItemsRendered) {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .background(DarkGray),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            itemsIndexed(
                                items = filteredCoins,
                                key = { _, coinModel -> coinModel.id }) { index, coinModel ->

                                val chart by remember{
                                    derivedStateOf { coinChart.takeIf { it.isNotEmpty() && it.size > index } }
                                }

                                CoinsItem(
                                    currencySymbol = coinsState.coinCurrencyPreference.currencySymbol ?: "N/A",
                                    coinModel = coinModel,
                                    chartModel = chart?.get(index),
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
                    color = Green800
                )
            }


            if (coinList.isEmpty() && !coinsState.hasInternet) {
                NoInternetScreen(onTryButtonClick = {
                    if (context.hasInternetConnection()) {
                        coinsViewModel.onEvent(event = CoinsEvent.CloseNoInternetDisplay)
                        coinsViewModel.onEvent(event = CoinsEvent.RefreshInformation(coinsState.coinCurrencyPreference))
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
    var previousScrollOffset by remember(this) { mutableStateOf(firstVisibleItemScrollOffset) }
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


@Preview
@Composable
fun CoinScreenPreview() {

}

