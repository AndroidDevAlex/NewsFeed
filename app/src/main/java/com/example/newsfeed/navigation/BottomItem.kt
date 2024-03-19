package com.example.newsfeed.navigation

import com.example.newsfeed.R

sealed class BottomItem(
    val iconId: Int,
    val route: Routes
) {
    object NewsScreen : BottomItem(R.drawable.view, Routes.NEWS_SCREEN)

    object Filter : BottomItem(R.drawable.filter, Routes.FILTER)

    object Bookmark : BottomItem(R.drawable.bookmark, Routes.BOOKMARK)
}