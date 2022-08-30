package com.dominic.coin_search.feature_coins.presentation.coins_news

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dominic.coin_search.core.util.ConnectionStatus
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_coins.presentation.coin_detail.components.openBrowser
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.NewsItemLarge
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.NewsItemSmall
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.NewsTitleSection
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.PagerIndicator
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.TopBar
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.FavoriteListEvent
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.FavoriteListUiEvent
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.FavoritesViewModel
import com.dominic.coin_search.feature_no_internet.presentation.NoInternetScreen
import com.dominic.coin_search.ui.theme.DarkGray
import com.dominic.coin_search.ui.theme.Green800
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun NewsScreen(
    innerPaddingValues: PaddingValues,
    newsViewModel: NewsViewModel = hiltViewModel(),
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController?
) {
    val state by newsViewModel.state.collectAsState()
    val context = LocalContext.current
    val pagerState = rememberPagerState()

    val openUrl = { url: String ->
        openBrowser(context, url)
    }

    val onToggleSaveButton: (Boolean, NewsModel) -> Unit = { isAlreadySaved, news ->
        favoritesViewModel.onEvent(
            event = if (isAlreadySaved) FavoriteListEvent.DeleteNews(news) else FavoriteListEvent.AddNews(
                news))
    }



    LaunchedEffect(true) {

        favoritesViewModel.eventFlow.collectLatest { savedListEvent ->
            when (savedListEvent) {
                is FavoriteListUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, savedListEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(innerPaddingValues)
            .background(DarkGray)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
                onRefresh = { newsViewModel.onEvent(event = NewsEvent.RefreshNews) }) {

                if (!state.isLoading) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 10.dp)) {

                        item {

                            if (state.trendingNews.isNotEmpty()) {
                                NewsTitleSection(
                                    title = "Trending News",
                                    modifier = Modifier.padding(bottom = 10.dp, top = 15.dp))
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val trendingNews = state.trendingNews.take(10)
                                HorizontalPager(
                                    count = trendingNews.size,
                                    state = pagerState) { pageIndex ->

                                    val (news, isSaved) = trendingNews[pageIndex]
                                    NewsItemLarge(
                                        isSavedNews = isSaved,
                                        newsModel = news,
                                        onItemClick = { openUrl(news.link!!) },
                                        onSaveClick = { isAlreadySaved ->
                                            onToggleSaveButton(isAlreadySaved, news)
                                        }
                                    )
                                }
                                PagerIndicator(
                                    pagerState = pagerState,
                                    modifier = Modifier.padding(vertical = 10.dp)
                                )

                            }

                        }


                        item {
                            val forYouNews = remember {
                                merge(
                                    state.bearishNews,
                                    state.bullishNews,
                                    state.handpickedNews)
                            }

                            if (forYouNews.isNotEmpty()) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()) {
                                    NewsTitleSection(
                                        title = "For you",
                                        modifier = Modifier.padding(
                                            bottom = 10.dp,
                                            top = 15.dp))

                                }
                            }
                            LazyRow(modifier = Modifier.padding(bottom = 12.dp)) {

                                items(
                                    items = forYouNews.toList(),
                                    key = { it.first.id }) { newsModel ->
                                    val (news, isSaved) = newsModel
                                    NewsItemSmall(
                                        modifier = Modifier.widthIn(290.dp),
                                        newsModel = news,
                                        onItemClick = { openUrl(news.link!!) },
                                        onSaveClick = { isAlreadySaved ->
                                            onToggleSaveButton(isAlreadySaved, news)
                                        },
                                        isSavedNews = isSaved
                                    )


                                }
                            }
                        }



                        if (state.latestNews.isNotEmpty()) {
                            item {
                                NewsTitleSection(
                                    title = "Latest News",
                                    modifier = Modifier.padding(bottom = 10.dp, top = 15.dp))
                            }

                        }
                        items(
                            items = state.latestNews,
                            key = { it.first.id }) { newsModel ->
                            val (news, isSaved) = newsModel
                            NewsItemSmall(
                                modifier = Modifier.padding(vertical = 2.dp),
                                newsModel = news,
                                onItemClick = { openUrl(news.link!!) },
                                onSaveClick = { isAlreadySaved ->
                                    onToggleSaveButton(isAlreadySaved, news)
                                },
                                isSavedNews = isSaved
                            )
                        }

                    }
                }
            }
        }


        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = Green800
            )
        }

        if (!state.hasInternet) {
            NoInternetScreen(onTryButtonClick = {
                if (ConnectionStatus.hasInternetConnection(context)) {
                    newsViewModel.onEvent(event = NewsEvent.CloseNoInternetDisplay)
                    newsViewModel.onEvent(event = NewsEvent.RefreshNews)
                }
            })
        }

        if (state.errorMessage.isNotEmpty()) {
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


fun <T> merge(first: List<T>, second: List<T>, third: List<T>): Set<T> {

    return first.plus(second).plus(third).toSet()
}