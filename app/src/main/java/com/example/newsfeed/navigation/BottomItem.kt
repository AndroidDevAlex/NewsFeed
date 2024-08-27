package com.example.newsfeed.navigation

import com.example.newsfeed.R

sealed class BottomItem(
    val iconId: Int,
    val screen: Screen
) {

    data object NewsScreen : BottomItem(R.drawable.view, Screen.Home)

    data object Filter : BottomItem(R.drawable.filter, Screen.Filter)

    data object Bookmark : BottomItem(R.drawable.bookmark, Screen.Bookmark)
}