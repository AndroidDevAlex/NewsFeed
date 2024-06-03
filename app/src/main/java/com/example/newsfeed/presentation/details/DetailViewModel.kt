package com.example.newsfeed.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.DetailRepository
import com.example.newsfeed.presentation.details.state.StateUI
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
    private val detailRepository: DetailRepository
): ViewModel() {

    private val _detailState = MutableStateFlow(DetailState(stateUI = StateUI.Loading))
    val detailState: StateFlow<DetailState> = _detailState

    fun loadNews(newsUrl: String) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val news = detailRepository.getNewsByUrl(newsUrl)
                _detailState.value = _detailState.value.copy(currentNews = news)
               } catch (e: Exception) {
                Log.e("log", "error during load news: $e")
            }
        }
    }

    fun toggleBookmark() {
        val news = _detailState.value.currentNews ?: return
        viewModelScope.launch(ioDispatcher) {
            try {
                if (news.isBookmarked) {
                    detailRepository.deleteNews(news)
                } else {
                    detailRepository.saveNews(news)
                }
                val updatedNews = news.copy(isBookmarked = !news.isBookmarked)
                _detailState.value = _detailState.value.copy(currentNews = updatedNews, isBookmarked = updatedNews.isBookmarked)
            } catch (e: Exception) {
                Log.e("log", "error during press bookmark: $e")
            }
        }
    }
}