package com.mathroda.dashcoin.feature_coins.presentation.coins_news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.core.util.Constants.BEARISH_NEWS
import com.mathroda.dashcoin.core.util.Constants.BULLISH_NEWS
import com.mathroda.dashcoin.core.util.Constants.HANDPICKED_NEWS
import com.mathroda.dashcoin.core.util.Constants.LATEST_NEWS
import com.mathroda.dashcoin.core.util.Constants.TRENDING_NEWS
import com.mathroda.dashcoin.feature_coins.domain.exceptions.CoinExceptions
import com.mathroda.dashcoin.feature_coins.domain.models.NewsDetailModel
import com.mathroda.dashcoin.feature_coins.domain.use_case.CoinUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val coinUseCases: CoinUseCases
): ViewModel() {


    private val _state = mutableStateOf(NewsState())
    val state by _state


    private val _eventFlow = MutableSharedFlow<NewsUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()




    init {
        loadNews()
    }
    private fun loadNews(){
        //todo: add Dispatchers.IO
        viewModelScope.launch {
            getNews(filter = HANDPICKED_NEWS, newsResult = { newsDetails ->
                _state.value = state.copy(isLoading = false, handPickedNews = newsDetails)
            })

            getNews(filter = TRENDING_NEWS, newsResult = { newsDetails ->
                _state.value = state.copy(isLoading = false, trendingNews = newsDetails)
            })

            getNews(filter = LATEST_NEWS, newsResult = { newsDetails ->
                _state.value = state.copy(isLoading = false, latestNews = newsDetails)
            })

            getNews(filter = BULLISH_NEWS, newsResult = { newsDetails ->
                _state.value = state.copy(isLoading = false, bullishNews = newsDetails)
            })

            getNews(filter = BEARISH_NEWS, newsResult = { newsDetails ->
                _state.value = state.copy(isLoading = false, bearishNews = newsDetails)
            })
        }
    }


    private suspend fun getNews(filter: String, newsResult: (List<NewsDetailModel>) -> Unit) {
        coroutineScope {

            runCatching {
                coinUseCases.getNews(filter)
            }.onSuccess { newsDetailFlow ->
                newsDetailFlow.onEach { newsDetails ->
                    _state.value = state.copy(isLoading = false)
                    newsResult(newsDetails)
                }.launchIn(this)
            }.onFailure { exception ->
                _state.value = state.copy(isLoading = false)

                when (exception) {
                    is CoinExceptions.UnexpectedErrorException -> {
                        _eventFlow.emit(value = NewsUiEvent.ShowToastMessage(exception.message!!))
                    }
                    is CoinExceptions.NoInternetException -> {
                        _eventFlow.emit(value = NewsUiEvent.ShowNoInternetScreen)
                    }
                }

                this.cancel()
            }
        }
    }


    fun onEvent(event: NewsEvent){
        when(event){
            is NewsEvent.RefreshNews -> {
                refreshNews()
            }
        }
    }

    private fun refreshNews() {
        viewModelScope.launch {
            _state.value = state.copy(isRefreshing = true)
            loadNews()
            _state.value = state.copy(isRefreshing = false)

        }
    }

}



