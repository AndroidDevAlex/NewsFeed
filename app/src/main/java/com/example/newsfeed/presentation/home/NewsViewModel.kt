package com.example.newsfeed.presentation.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.domain.useCase.FetchNewsUseCase
import com.example.newsfeed.domain.useCase.GetSavedNewsUseCase
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val getNewsUseCase: GetSavedNewsUseCase,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val fetchNewsUseCase: FetchNewsUseCase
) : ViewModel() {

    private val _newsListNews = MutableStateFlow(NewsState())
    val newsListNews: StateFlow<NewsState> = _newsListNews

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
            getNewsUseCase.getSavedNewsPaging()
                .cachedIn(this)
                .collect { pagingData ->
                    updateNewsList(flowOf(pagingData))
                }
        }
    }

    private fun refreshNewsFromServer() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            fetchNewsUseCase.refreshNews()
                .cachedIn(this)
                .collect { pagingData ->
                    updateNewsList(flowOf(pagingData))
                }
        }
    }

    fun refreshScreen() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {

            showProgressBar()

            fetchNewsUseCase.refreshNews()
                .cachedIn(this)
                .collect { updatedNews ->
                    updateNewsList(flowOf(updatedNews))
                }
        }
    }

    private fun showProgressBar() {
        _newsListNews.update { it.copy(isRefreshing = true) }
    }

    private fun errorHandling() {
        _newsListNews.update { it.copy(isRefreshing = false) }
    }

    private fun updateNewsList(newList: Flow<PagingData<ItemNewsUi>>) {
        _newsListNews.value = NewsState(
            newList = newList
        )
    }

    fun pressBookmark(news: ItemNewsUi) {
        viewModelScope.launch(ioDispatcher) {
            val isBookmarked = !news.isBookmarked
            newsRepository.toggleBookmark(news.copy(isBookmarked = isBookmarked))

            val updatedNewsList = _newsListNews.value.newList.map { pagingData ->
                pagingData.map { newsUi ->
                    if (newsUi.id == news.id) {
                        newsUi.copy(isBookmarked = isBookmarked)
                    } else {
                        newsUi
                    }
                }
            }

            _newsListNews.value = _newsListNews.value.copy(
                newList = updatedNewsList
            )

        }
    }

    fun showDialog(news: ItemNewsUi) {
        _newsListNews.update { it.copy(isDialogVisible = true, selectedNews = news) }
    }

    fun hideDialog() {
        _newsListNews.update { it.copy(isDialogVisible = false, selectedNews = null) }
    }
}