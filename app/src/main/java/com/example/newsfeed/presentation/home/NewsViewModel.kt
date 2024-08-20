package com.example.newsfeed.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsfeed.domain.useCase.homeCase.GetAllNewsSourcesUseCase
import com.example.newsfeed.domain.useCase.homeCase.FetchNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.ToggleBookmarkUseCase
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.internetConection.NetworkStateObserver
import com.example.newsfeed.util.ConstantsSourceNames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NewsViewModel @Inject constructor(
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val fetchNewsUseCase: FetchNewsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    private val getAllNewsSourcesUseCase: GetAllNewsSourcesUseCase,
    networkStateObserver: NetworkStateObserver
) : ViewModel() {

    private val _selectedSources = MutableStateFlow(listOf(ConstantsSourceNames.HABR, ConstantsSourceNames.REDDIT))
    val selectedSources: StateFlow<List<String>> = _selectedSources

    private val _allNews = MutableStateFlow<PagingData<ItemNewsUi>>(PagingData.empty())
    val allNews: StateFlow<PagingData<ItemNewsUi>> = _allNews

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val isConnected: StateFlow<Boolean> = networkStateObserver.isConnected

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("log", "error during refresh screen: $exception")
        errorHandling()
    }

    fun showNewsFeedBySelectedSources() {
        loadAndShowNews()
    }

    private fun loadAndShowNews() {
        getNews()
        refreshNewsFromServer()
    }

    private fun getNews() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            getAllNewsSourcesUseCase.getAllNewsBySelectedSources()
                .cachedIn(this)
                .collect { pagingNews ->
                    updateNewsList(pagingNews)
                }
        }
    }

    private fun refreshNewsFromServer() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            fetchNewsUseCase.fetchNews()

            getAllNewsSourcesUseCase.getAllNewsBySelectedSources()
                .cachedIn(this)
                .collect { pagingNews ->
                    updateNewsList(pagingNews)
                }
        }
    }

    fun refreshScreen() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {

            showProgressBar()

            fetchNewsUseCase.fetchNews()

            getAllNewsSourcesUseCase.getAllNewsBySelectedSources()
                .cachedIn(this)
                .collect { pagingNews ->
                    updateNewsList(pagingNews)
                }
        }
    }

    private fun showProgressBar() {
        _isRefreshing.value = true
    }

    private fun errorHandling() {
        _isRefreshing.value = false
    }

    fun pressBookmark(news: ItemNewsUi) {
        viewModelScope.launch(ioDispatcher) {
            val isBookmarked = !news.isBookmarked
            toggleBookmarkUseCase.toggleBookmark(news.copy(isBookmarked = isBookmarked))
        }
    }

    private fun updateNewsList(newList: PagingData<ItemNewsUi>) {
        _allNews.value = newList
        _isRefreshing.value = false
    }
}