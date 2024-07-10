package com.example.newsfeed.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsfeed.domain.useCase.homeCase.FetchNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.GetSavedNewsUseCase
import com.example.newsfeed.domain.useCase.homeCase.ToggleBookmarkUseCase
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
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
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ViewModel() {

    private val _allNews = MutableStateFlow<PagingData<ItemNewsUi>>(PagingData.empty())
    val allNews: StateFlow<PagingData<ItemNewsUi>> = _allNews

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()


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
            getSavedNewsUseCase.getNewsPagingSource()
                .cachedIn(this)
                .collect { pagingNews ->
                    updateNewsList(pagingNews, false)
                }
        }
    }

    private fun refreshNewsFromServer() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            fetchNewsUseCase.refreshNews()
                .cachedIn(this)
                .collect { pagingData ->
                    updateNewsList(pagingData, false)
                }
        }
    }

    fun refreshScreen() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {

            showProgressBar()

            fetchNewsUseCase.refreshNews()
                .cachedIn(this)
                .collect { updatedNews ->
                    updateNewsList(updatedNews, false)
                }
        }
    }

    private fun showProgressBar() {
        _isRefreshing.value = true
    }

    private fun errorHandling() {
        _isRefreshing.value = false
    }

    private fun updateNewsList(newList: PagingData<ItemNewsUi>, isRefreshing: Boolean) {
        _allNews.value = newList
        _isRefreshing.value = isRefreshing
    }

    fun pressBookmark(news: ItemNewsUi) {
        viewModelScope.launch(ioDispatcher) {
            val isBookmarked = !news.isBookmarked
            toggleBookmarkUseCase.toggleBookmark(news.copy(isBookmarked = isBookmarked))
        }
    }
}