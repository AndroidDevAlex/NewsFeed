package com.example.newsfeed.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsfeed.domain.useCase.bookmarkCase.GetBookmarkNewsUseCase
import com.example.newsfeed.domain.useCase.bookmarkCase.BookmarkToggleUseCase
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
class BookmarkViewModel @Inject constructor(
    private val getSavedNewsUseCase: GetBookmarkNewsUseCase,
    private val toggleBookmarkUseCase: BookmarkToggleUseCase,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    networkStateObserver: NetworkStateObserver
) : ViewModel() {

    private val _bookmarkedPagingDataFlow =
        MutableStateFlow<PagingData<ItemNewsUi>>(PagingData.empty())
    val bookmarkedPagingDataFlow: StateFlow<PagingData<ItemNewsUi>> = _bookmarkedPagingDataFlow

    val isConnected: StateFlow<Boolean> = networkStateObserver.isConnected

    init {
        getSavedNews()
    }

    private fun getSavedNews() {
        viewModelScope.launch(ioDispatcher) {
            getSavedNewsUseCase.getNewsPagingSource().cachedIn(this)
                .collect { pagingNews ->
                    _bookmarkedPagingDataFlow.value = pagingNews
                }
        }
    }

    fun onBookmarkClicked(news: ItemNewsUi) {
        viewModelScope.launch(ioDispatcher) {

            val updatedNews = news.copy(isBookmarked = false)
            toggleBookmarkUseCase.toggleBookmark(updatedNews)
        }
    }
}