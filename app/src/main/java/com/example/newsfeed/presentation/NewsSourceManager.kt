package com.example.newsfeed.presentation

import com.example.newsfeed.util.NewsSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NewsSourceManager @Inject constructor() {

    private val _selectedSources = MutableStateFlow(listOf(NewsSource.REDDIT, NewsSource.HABR))
    val selectedSources: StateFlow<List<NewsSource>> = _selectedSources

    fun setSelectedSources(sources: List<NewsSource>) {
        _selectedSources.value = sources
    }
}