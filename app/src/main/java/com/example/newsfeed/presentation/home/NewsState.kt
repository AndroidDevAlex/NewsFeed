package com.example.newsfeed.presentation.home

import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.state.StateUI

data class NewsState(
    val uiState: StateUI,
    val isBookmarked: Boolean = false,
    val newsList: List<NewsUi> = emptyList(),
    val isRefreshing: Boolean = false
)