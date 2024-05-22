package com.example.newsfeed.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.state.StateUI
import com.example.newsfeed.util.RequestResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val getAllNewsUseCase: GetAllNewsUseCase,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val newsPagingDataFlow: Flow<PagingData<NewsUi>> = getAllNewsUseCase.getSavedNewsPaging()
        .cachedIn(viewModelScope)

    private val _newsListNews = MutableStateFlow(NewsState(StateUI.None))
    val newsListNews: StateFlow<NewsState> = _newsListNews

    init {
        getAllNews()
    }

    private fun getAllNews() {
        viewModelScope.launch {
            _newsListNews.value = NewsState(StateUI.Loading(true))

            getAllNewsUseCase.fetchNews().collect { result ->
                if (result is RequestResult.Success) {
                    updateNewsState()
                } else {
                    _newsListNews.value = NewsState(StateUI.Error())
                }
            }
        }
    }

    private fun updateNewsState() {
        viewModelScope.launch {
            getAllNewsUseCase.getSavedNewsPaging().collect { pagingData ->

                val newsList = mutableListOf<NewsUi>()
                pagingData.map { newsUi ->
                    newsList.add(newsUi)
                }

                _newsListNews.value = NewsState(StateUI.Success(newsList), newsList = newsList)
            }
        }
    }

        fun pressBookmark(news: NewsUi) {

            viewModelScope.launch(ioDispatcher) {

                val currentState = _newsListNews.value
                val isBookmarked = currentState.isBookmarked

                if (isBookmarked) {
                    newsRepository.deleteNews(news)
                } else {
                    newsRepository.saveNews(news)
                }

                val updatedNewsList = _newsListNews.value.newsList.map { item ->
                    if (item.id == news.id) {
                        item.copy(isBookmarked = !item.isBookmarked)
                    } else {
                        item
                    }
                }
                _newsListNews.value =
                    currentState.copy(newsList = updatedNewsList, isBookmarked = !isBookmarked)
            }
        }

        fun refreshScreen() {
            viewModelScope.launch {
                getAllNewsUseCase.fetchNews().collect { refreshedResult ->
                    if (refreshedResult is RequestResult.Success) {

                        val currentState = _newsListNews.value
                        val currentNewsList = currentState.newsList

                        val newSavedListPaging = getAllNewsUseCase.getSavedNewsPaging().first()

                        val newSavedList = newSavedListPaging.mapPagingToList()

                        val combinedNews = combineNews(currentNewsList, newSavedList)

                        _newsListNews.value = currentState.copy(
                            uiState = StateUI.Success(combinedNews),
                            newsList = combinedNews,
                            showDialog = false
                        )

                    } else {
                        _newsListNews.value = _newsListNews.value.copy(showDialog = true)
                       // _newsListNews.value = NewsState(StateUI.Error()).copy(showDialog = true)
                    }
                }
            }
        }

    private fun <T : Any> PagingData<T>.mapPagingToList(): List<T> {
        val list = mutableListOf<T>()
        this.map { data ->
            list.addAll(listOf(data))
        }
        return list
    }

        // for visual display refresh
        fun dismissErrorDialog() {
            _newsListNews.value = _newsListNews.value.copy(showDialog = false)
            refreshScreen()
        }

        private fun combineNews(
            currentNewsList: List<NewsUi>,
            newSavedNews: List<NewsUi>
        ): List<NewsUi> {
            val updatedNewsList = (currentNewsList + newSavedNews).distinctBy { it.id }
            return updatedNewsList
        }
    }