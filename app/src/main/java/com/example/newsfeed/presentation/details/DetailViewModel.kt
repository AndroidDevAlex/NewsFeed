package com.example.newsfeed.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.DetailRepository
import com.example.newsfeed.presentation.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DetailViewModel @Inject constructor(
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val detailRepository: DetailRepository
): ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState>
        get() = _detailState.asStateFlow()

    fun toggleBookmark(news: NewsUi) {
        val currentState = _detailState.value
        val newState = currentState.copy(isBookmarked = !currentState.isBookmarked)
        _detailState.value = newState

        viewModelScope.launch(ioDispatcher) {
            if (newState.isBookmarked) {
                detailRepository.saveNews(news)
            } else {
                detailRepository.deleteNews(news.id)
            }
        }
    }
}