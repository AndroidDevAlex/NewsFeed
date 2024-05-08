package com.example.newsfeed.presentation.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.state.StateUI
import com.example.newsfeed.state.toStateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val getAllNewsUseCase: GetAllNewsUseCase,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _newsListNews = MutableStateFlow(NewsState(StateUI.None))
    val newsListNews: StateFlow<NewsState> = _newsListNews

    init {
        getAllNews()
    }

    private fun getAllNews() {
        _newsListNews.value = NewsState(StateUI.Loading(true))
        viewModelScope.launch {
            getAllNewsUseCase.invoke().collect { result ->
                _newsListNews.value = NewsState(result.toStateUI())
            }
        }
    }

    fun pressBookmark(news: NewsUi) {
        val currentState = _newsListNews.value
        val isBookmarked = currentState.isBookmarked

        viewModelScope.launch(ioDispatcher) {

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
            _newsListNews.value = _newsListNews.value.copy(newsList = updatedNewsList)
        }
    }

    fun refreshScreen() {
        viewModelScope.launch{
            getAllNewsUseCase.invoke().collect { result ->
                _newsListNews.value = NewsState(result.toStateUI())
            }
        }
    }
}