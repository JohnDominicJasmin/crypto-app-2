package com.dominic.coin_search.feature_coins.presentation.coins_news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.core.util.Constants.BEARISH_NEWS
import com.dominic.coin_search.core.util.Constants.BULLISH_NEWS
import com.dominic.coin_search.core.util.Constants.HANDPICKED_NEWS
import com.dominic.coin_search.core.util.Constants.LATEST_NEWS
import com.dominic.coin_search.core.util.Constants.TRENDING_NEWS
import com.dominic.coin_search.feature_coins.domain.exceptions.CoinExceptions
import com.dominic.coin_search.feature_coins.domain.models.NewsDetailModel
import com.dominic.coin_search.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases
) : ViewModel() {


    private val _state = MutableStateFlow(NewsState())
    val state = _state.asStateFlow()


    init {
        loadNews()
    }

    private fun loadNews() {


            getNews(filter = HANDPICKED_NEWS, newsResult = { newsDetails ->
                _state.update { it.copy(isLoading = false, handPickedNews = newsDetails) }
            })

            getNews(filter = TRENDING_NEWS, newsResult = { newsDetails ->
                _state.update { it.copy(isLoading = false, trendingNews = newsDetails) }
            })

            getNews(filter = LATEST_NEWS, newsResult = { newsDetails ->
                _state.update { it.copy(isLoading = false, latestNews = newsDetails) }
            })

            getNews(filter = BULLISH_NEWS, newsResult = { newsDetails ->
                _state.update { it.copy(isLoading = false, bullishNews = newsDetails) }
            })

            getNews(filter = BEARISH_NEWS, newsResult = { newsDetails ->
                _state.update { it.copy(isLoading = false, bearishNews = newsDetails) }
            })
        }



    private  fun getNews(filter: String, newsResult: (List<NewsDetailModel>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            runCatching {
                _state.update { it.copy(isLoading = true) }
                coinUseCases.getNews(filter)

            }.onSuccess(newsResult)
                .onFailure { exception ->
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
    }


    fun onEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.RefreshNews -> {
                refreshNews()
            }
            is NewsEvent.CloseNoInternetDisplay -> {
                _state.update { it.copy(hasInternet = true, isRefreshing = false) }
            }
            is NewsEvent.EnteredSearchQuery -> {
                _state.update { it.copy(searchQuery = event.searchQuery) }
            }
        }
    }

    private fun refreshNews() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            loadNews()
            _state.update { it.copy(isRefreshing = false) }

        }
    }

}



