package com.example.newsfeed.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("news")
    data object Filter : Screen("filter")
    data object Bookmark : Screen("bookmark")
    data object Details : Screen("details")
}