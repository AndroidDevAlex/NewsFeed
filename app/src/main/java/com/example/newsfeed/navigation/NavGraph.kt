package com.example.newsfeed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.presentation.bookmark.BookmarkScreen
import com.example.newsfeed.presentation.details.DetailsScreen
import com.example.newsfeed.presentation.filter.FilterScreen
import com.example.newsfeed.presentation.home.NewsScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            NewsScreen(navHostController)
        }
        composable(Screen.Filter.route) {
            FilterScreen()
        }
        composable(Screen.Bookmark.route) {
            BookmarkScreen(navHostController)
        }
        composable(Screen.Details.route) { currentStack ->
            val args = requireNotNull(currentStack.arguments)
            val newsUrl = args.getString("newsUrl") ?: ""
            DetailsScreen(navHostController, newsUrl)
        }
    }
}