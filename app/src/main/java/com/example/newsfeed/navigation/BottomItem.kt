package com.example.newsfeed.navigation

import com.example.newsfeed.R

sealed class BottomItem(
    val iconId: Int,
    val screen: Screen
) {

    object NewsScreen : BottomItem(R.drawable.view, Screen.Home)

    object Filter : BottomItem(R.drawable.filter, Screen.Filter)

    object Bookmark : BottomItem(R.drawable.bookmark, Screen.Bookmark)
}