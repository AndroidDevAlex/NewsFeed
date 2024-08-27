package com.example.newsfeed.presentation.details


import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.presentation.details.state.StateUI

data class DetailState(
    val isBookmarked: Boolean = false,
    var stateUI: StateUI,
    val currentNews: ItemNewsUi? = null,
    val scrollPosition: Int = 0
)