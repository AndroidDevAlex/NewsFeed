package com.example.newsfeed.presentation.bookmark

import com.example.newsfeed.presentation.NewsUi

data class BookmarkState(
val news: List<NewsUi> = emptyList()
)