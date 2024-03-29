package com.example.newsfeed.navigation

enum class Routes {
    NEWS_SCREEN,
    FILTER,
    BOOKMARK,
    DETAILS
}

sealed class Screen(val rout: String) {
    object Home : Screen("news_screen")
    object Filter : Screen("filter")
    object Bookmark : Screen("bookmark")
    object Details : Screen("details")
}