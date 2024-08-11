package com.example.newsfeed.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.newsfeed.domain.useCase.homeCase.FetchNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.ToggleBookmarkUseCase
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.internetConection.NetworkStateObserver
import com.example.newsfeed.data.remote.NewsFilterManager
import com.example.newsfeed.util.NewsSource
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
    networkStateObserver: NetworkStateObserver,
    private val newsFilterManager: NewsFilterManager
) : ViewModel() {

    val selectedSources: StateFlow<List<NewsSource>> = newsFilterManager.selectedSources
    val allNews: StateFlow<PagingData<ItemNewsUi>> = newsFilterManager.filteredNews

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val isConnected: StateFlow<Boolean> = networkStateObserver.isConnected

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("log", "error during refresh screen: $exception")
        errorHandling()
    }

    init {
        getAllSavedNews()
    }

    private fun getAllSavedNews() {
        getSavedNews()
        refreshNewsFromServer()
    }

    private fun getSavedNews() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            newsFilterManager.refreshNewsBySource(selectedSources.value)
        }
    }

    private fun refreshNewsFromServer() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            fetchNewsUseCase.fetchNews(selectedSources.value)
            newsFilterManager.refreshNewsBySource(selectedSources.value)
        }
    }

    fun refreshScreen() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {

            showProgressBar()

            fetchNewsUseCase.fetchNews(selectedSources.value)
            newsFilterManager.refreshNewsBySource(selectedSources.value)
            hideProgressBar()
        }
    }

    private fun showProgressBar() {
        _isRefreshing.value = true
    }

    private fun hideProgressBar() {
        _isRefreshing.value = false
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
}