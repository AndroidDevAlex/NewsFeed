package com.example.newsfeed.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.state.StateUI
import com.example.newsfeed.state.toStateUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val getAllNewsUseCase: GetAllNewsUseCase,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val newsListNews: StateFlow<StateUI> = getAllNewsUseCase()//.invoke()
        .map { it.toStateUI() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), StateUI.None)

    fun onBookmarkClicked(news: NewsUi) {
        viewModelScope.launch(ioDispatcher) {
            if (news.isBookmarked) {
                newsRepository.deleteNews(news.id)
            } else {
                newsRepository.saveNews(news)
            }
        }
    }

    fun forceUpdate() { // надо получить данные и смерджить старый стейт с новым
        viewModelScope.launch(ioDispatcher) {
            newsRepository.fetchLatest()
        }
    }
}