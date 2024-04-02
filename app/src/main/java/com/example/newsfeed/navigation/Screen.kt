package com.example.newsfeed.navigation

sealed class Screen(val route: String) {
    object Home : Screen("news")
    object Filter : Screen("filter")
    object Bookmark : Screen("bookmark")
    object Details : Screen("details")
}