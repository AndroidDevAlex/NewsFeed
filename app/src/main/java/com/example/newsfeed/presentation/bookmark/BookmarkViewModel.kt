package com.example.newsfeed.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookmarkViewModel @Inject constructor (
    private val bookmarkRepository: BookmarkRepository,
   // private val bookmarkUseCase: GetAllSavedNews,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _state = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

   // val savedListNews = bookmarkUseCase().shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    init {
        updateNews()
    }

   /* private fun updateNews(news: List<NewsUi>) {
        _state.update {
            it.copy(news = news)
        }*/

   private fun updateNews() {
        viewModelScope.launch(ioDispatcher) {
            bookmarkRepository.getSavedNews().collect{newsList ->
                val newState = _state.value.copy(news = newsList)
                _state.value = newState
                }
            }
        }

    fun onBookmarkClicked(news: NewsUi) {
        viewModelScope.launch(ioDispatcher) {
            val newState = if (news.isBookmarked) {
                       // дописать метод для удаления
                _state.value.copy(news = _state.value.news.filterNot { it.id == news.id })
            } else {
                      // дописать метод для сохрания
                _state.value.copy(news = _state.value.news + news)
            }
            _state.value = newState
        }
    }
    }