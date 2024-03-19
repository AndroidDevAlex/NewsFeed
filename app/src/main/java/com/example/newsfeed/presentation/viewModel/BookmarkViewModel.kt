package com.example.newsfeed.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.data.remote.News
import com.example.newsfeed.domain.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookmarkViewModel @Inject constructor (
    private val bookmarkRepository: BookmarkRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _savedNews = MutableStateFlow<List<News>>(emptyList())
    val savedNews = _savedNews.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            bookmarkRepository.getSavedNewsFromLocalDB().collect{savedNews ->
      _savedNews.value = savedNews
            }
        }
    }
}