package com.example.newsfeed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsfeed.presentation.bookmark.BookmarkScreen
import com.example.newsfeed.presentation.details.DetailsScreen
import com.example.newsfeed.presentation.filter.FilterScreen
import com.example.newsfeed.presentation.home.NewsScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.Home.rout) {

        composable(Screen.Home.rout) {
            NewsScreen(navHostController)
        }
        composable(Screen.Filter.rout) {
            FilterScreen()
        }
        composable(Screen.Bookmark.rout) {
            BookmarkScreen(navHostController)
        }
        composable(Screen.Details.rout) {
            DetailsScreen(navHostController)
        }
    }
}