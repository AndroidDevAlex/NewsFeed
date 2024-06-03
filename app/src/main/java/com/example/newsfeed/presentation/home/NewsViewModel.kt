package com.example.newsfeed.presentation.home


import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.domain.useCase.NewsUseCase
import com.example.newsfeed.presentation.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsUseCase: NewsUseCase,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _newsListNews = MutableStateFlow(NewsState())
    val newsListNews: StateFlow<NewsState> = _newsListNews

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("log", "error during refresh screen: $exception")
        _newsListNews.value = _newsListNews.value.copy(showDialog = true, isRefreshing = false)
    }

    init {
        getAllSavedNews()
        displayNews()
    }

    private fun getAllSavedNews() {
        viewModelScope.launch(ioDispatcher) {
            try {
                newsUseCase.getSavedNewsPaging()
                    .cachedIn(viewModelScope)
                    .collect { pagingData ->
                        _newsListNews.value = NewsState(newList = flowOf(pagingData))
                    }
            } catch (e: Exception) {
                Log.e("log", "Error get saved news: $e")
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun refreshScreen() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            _newsListNews.value = _newsListNews.value.copy(isRefreshing = true)

            try {
                val fetchNews = async { newsUseCase.invoke() }
                fetchNews.await()

                val newSavedNews = mutableListOf<NewsUi>()
                newsUseCase.getSavedNewsPaging().collect { savedPagingNews ->
                    newSavedNews.addAll(savedPagingNews.mapPagingToList())
                }

                _newsListNews.value = _newsListNews.value.copy(
                    newList = flowOf(PagingData.from(newSavedNews)),
                    showDialog = false,
                    isRefreshing = false
                )

            } catch (e: Exception) {
                Log.e("log", "error during refresh screen: $e")
            }
        }
    }

    private fun displayNews() {
        viewModelScope.launch(ioDispatcher + exceptionHandler) {
            try {
               // val savedNews = async { getAllSavedNews() }
                val fetchNews = async { newsUseCase.invoke() }

              //  savedNews.await()
                fetchNews.await()

                 updateNewsState()

            } catch (e: Exception) {
                Log.e("log", "Error during displaying screen: $e")
            }
        }
    }

    private suspend fun updateNewsState() {
        try {
            val savedNews = mutableListOf<NewsUi>()
            newsUseCase.getSavedNewsPaging().collect { savedPagingNews ->
                savedNews.addAll(savedPagingNews.mapPagingToList())
            }

            val currentNewsList = mutableListOf<NewsUi>()
            _newsListNews.value.newList.collect { currentPagingData ->
                currentNewsList.addAll(currentPagingData.mapPagingToList())
            }

            val updatedCombinedNewsList = combineNews(currentNewsList, savedNews)
            val updatedList = flowOf(PagingData.from(updatedCombinedNewsList))

            _newsListNews.value = NewsState(newList = updatedList)

        } catch (e: Exception) {
            Log.e("log", "Error during updating screen:  $e")
        }
    }

    private fun combineNews(
        currentNewsList: List<NewsUi>,
        newSavedNews: List<NewsUi>
    ): List<NewsUi> {
        return (currentNewsList + newSavedNews).distinctBy { it.id }
    }

    private fun <T : Any> PagingData<T>.mapPagingToList(): List<T> {
        val list = mutableListOf<T>()
        this.map { data ->
            list.addAll(listOf(data))
        }
        return list
    }

    fun dismissErrorDialog() {
        _newsListNews.value = _newsListNews.value.copy(showDialog = false)
        refreshScreen()
    }

    fun pressBookmark(news: NewsUi) {
        viewModelScope.launch(ioDispatcher) {

            try {
                if (news.isBookmarked) {
                    newsRepository.deleteNews(news)
                } else {
                    newsRepository.saveNews(news)
                }

                val updatedNewsList = _newsListNews.value.newList.map { pagingData ->
                    pagingData.map { newsUi ->
                        if (newsUi.id == news.id) {
                            newsUi.copy(isBookmarked = !news.isBookmarked)
                        } else {
                            newsUi
                        }
                    }
                }

                _newsListNews.value = _newsListNews.value.copy(newList = updatedNewsList)
            } catch (e: Exception) {
                Log.e("log", "Error during press bookmark: $e")
            }
        }
    }
}