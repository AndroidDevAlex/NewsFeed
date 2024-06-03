package com.example.newsfeed.presentation.details


import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.presentation.details.state.StateUI

data class DetailState(
    val isBookmarked: Boolean = true,
    var stateUI: StateUI,
    val currentNews: NewsUi? = null,
)