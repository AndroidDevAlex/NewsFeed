package com.example.newsfeed.presentation.bookmark


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _bookmarkedPagingDataFlow = MutableStateFlow<PagingData<NewsUi>>(PagingData.empty())
    val bookmarkedPagingDataFlow: StateFlow<PagingData<NewsUi>> = _bookmarkedPagingDataFlow

    init {
        getSavedNews()
    }

    private fun getSavedNews() {
        viewModelScope.launch(ioDispatcher) {
           bookmarkRepository.getSavedNewsPagingSource().cachedIn(viewModelScope).collect{ pagingNews->
               _bookmarkedPagingDataFlow.value = pagingNews
           }
        }
    }

    fun onBookmarkClicked(news: NewsUi) {
        viewModelScope.launch(ioDispatcher) {
            bookmarkRepository.deleteNews(news)
        }
    }
}