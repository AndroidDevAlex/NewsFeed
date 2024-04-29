package com.example.newsfeed.presentation.home


import com.example.newsfeed.state.StateUI

data class NewsState(
    val newsState: StateUI,
    val isBookmarked: Boolean = false
)