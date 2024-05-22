package com.example.newsfeed.presentation.bookmark


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val bookmarkedPagingDataFlow: Flow<PagingData<NewsUi>> = bookmarkRepository.getSavedNewsPagingSource()
        .cachedIn(viewModelScope)

   /* private val _state = MutableStateFlow(BookmarkState())
    val state: StateFlow<BookmarkState> = _state.asStateFlow()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch(ioDispatcher) {
            val savedNews = bookmarkRepository.getSavedNews()
            val newState = BookmarkState(news = savedNews)
            _state.value = newState

        }
    }*/

    fun onBookmarkClicked(news: NewsUi) {
        viewModelScope.launch(ioDispatcher) {
            bookmarkRepository.deleteNews(news)
           /* _state.value =
                _state.value.copy(news = _state.value.news.filterNot { it.id == news.id })*/
        }
    }
}