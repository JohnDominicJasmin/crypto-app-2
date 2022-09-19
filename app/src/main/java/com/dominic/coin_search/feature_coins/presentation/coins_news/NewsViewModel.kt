package com.dominic.coin_search.feature_coins.presentation.coins_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.core.util.Constants.BEARISH_NEWS
import com.dominic.coin_search.core.util.Constants.BULLISH_NEWS
import com.dominic.coin_search.core.util.Constants.HAND_PICKED
import com.dominic.coin_search.core.util.Constants.LATEST_NEWS
import com.dominic.coin_search.core.util.Constants.TRENDING_NEWS
import com.dominic.coin_search.feature_coins.domain.exceptions.CoinExceptions
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_coins.domain.use_case.CoinUseCases
import com.dominic.coin_search.feature_favorites.domain.use_case.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases,
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NewsState())
    val state = _state.asStateFlow()


    private val cacheListId = mutableListOf<String>()
    init {
        loadNews()
    }

    private fun loadNews() {



        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            getNews(filter = TRENDING_NEWS) { newsDetails ->
                _state.update { it.copy(trendingNews = TrendingNews(newsDetails)) }
            }

            getNews(filter = HAND_PICKED) { newsDetails ->
                _state.update { it.copy(handpickedNews = HandpickedNews(newsDetails)) }
            }

            getNews(filter = LATEST_NEWS, newsResult = { newsDetails ->
                _state.update { it.copy(latestNews = LatestNews(newsDetails)) }
            })

            getNews(filter = BEARISH_NEWS, newsResult = { newsDetails ->
                _state.update { it.copy(bearishNews = BearishNews(newsDetails)) }
            })

            getNews(filter = BULLISH_NEWS, newsResult = { newsDetails ->
                _state.update { it.copy(bullishNews = BullishNews(newsDetails)) }
            })

        }.invokeOnCompletion {
            _state.update { it.copy(isLoading = false) }
        }

    }


    private suspend fun getNews(filter: String, newsResult: (List<Pair<NewsModel, Boolean>>) -> Unit) {
        coroutineScope {
            val favoriteNews = favoriteUseCase.getNews().distinctUntilChanged().first()
            runCatching {
                coinUseCases.getNews(filter).distinctUntilChanged().first()
            }.onSuccess { newsDetails ->
                mutableListOf<Pair<NewsModel, Boolean>>().run {
                    newsDetails.forEach { newsDetail ->
                        if(newsDetail.id !in cacheListId){
                            add(Pair(newsDetail, favoriteNews.any { it.id == newsDetail.id }))
                        }
                        cacheListId.add(newsDetail.id)

                    }.also {
                        newsResult(this)
                    }
                }
            }.onFailure { handleException(it) }
        }
    }

    private suspend fun handleException(exception: Throwable){
        coroutineScope {
            _state.update { it.copy(isLoading = false) }
            when (exception) {
                is CoinExceptions.UnexpectedErrorException -> {
                    _state.update { it.copy(errorMessage = exception.message!!) }
                }
                is CoinExceptions.NoInternetException -> {
                    _state.update { it.copy(hasInternet = false) }
                }
            }
            this.cancel()
        }
    }

    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.RefreshNews -> {
                refreshNews()
            }
            is NewsEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true, isRefreshing = false) }
            }

        }
    }

    private fun refreshNews() {
        viewModelScope.launch {
            cacheListId.clear()
            _state.update { it.copy(isRefreshing = true) }
            loadNews()
            _state.update { it.copy(isRefreshing = false) }

        }
    }

}


