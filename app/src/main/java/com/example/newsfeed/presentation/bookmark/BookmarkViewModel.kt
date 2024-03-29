package com.example.newsfeed.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import com.example.newsfeed.data.remote.fromDbToNewsUi as fromDbToNewsUi

@HiltViewModel
class BookmarkViewModel @Inject constructor (
    private val bookmarkRepository: BookmarkRepository,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _savedNews = MutableStateFlow<List<NewsUi>>(emptyList())
    val savedNews = _savedNews.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            bookmarkRepository.getSavedNewsFromLocalDB().collect{news ->
      _savedNews.value = news.map { it.fromDbToNewsUi()!! }}
            }
        }
    }