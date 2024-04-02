package com.example.newsfeed.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import com.example.newsfeed.data.remote.mapFromDBToUi as fromDbToNewsUi

@HiltViewModel
class BookmarkViewModel @Inject constructor (
    private val bookmarkRepository: BookmarkRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _state = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

    init {
        updateNews()
    }

    private fun updateNews() {
        viewModelScope.launch(ioDispatcher) {
            bookmarkRepository.getSavedNewsFromLocalDB().collect { news ->
                _state.value = BookmarkState(
                    news.map { it.fromDbToNewsUi()})
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