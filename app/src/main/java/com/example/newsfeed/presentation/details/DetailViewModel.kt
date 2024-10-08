package com.example.newsfeed.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.data.remote.repository.DetailRepository
import com.example.newsfeed.presentation.details.state.StateUI
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.internetConection.NetworkStateObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel @Inject constructor(
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val detailRepository: DetailRepository,
    networkStateObserver: NetworkStateObserver
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState(stateUI = StateUI.Loading))
    val detailState: StateFlow<DetailState> = _detailState

    val isConnected: StateFlow<Boolean> = networkStateObserver.isConnected

    fun loadNews(newsUrl: String) {
        viewModelScope.launch(ioDispatcher) {
            val news = detailRepository.getNewsByUrl(newsUrl)
            updateDetailNews(news)
        }
    }

    fun toggleBookmark() {
        val news = _detailState.value.currentNews ?: return
        val updatedNews = news.copy(isBookmarked = !news.isBookmarked)

        viewModelScope.launch(ioDispatcher) {
            detailRepository.toggleBookmark(updatedNews)
            updateDetailNews(updatedNews)
        }
    }

    fun updateScrollPosition(position: Int) {
        _detailState.value = _detailState.value.copy(
            scrollPosition = position
        )
    }

    fun updateStateUi(stateUI: StateUI) {
        _detailState.value = _detailState.value.copy(
            stateUI = stateUI
        )
    }

    private fun updateDetailNews(news: ItemNewsUi) {
        _detailState.value = _detailState.value.copy(
            currentNews = news,
            isBookmarked = news.isBookmarked
        )
    }
}