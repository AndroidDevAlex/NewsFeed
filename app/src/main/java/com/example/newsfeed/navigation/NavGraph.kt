package com.example.newsfeed.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsfeed.presentation.screens.BookmarkScreen
import com.example.newsfeed.presentation.screens.DetailsScreen
import com.example.newsfeed.presentation.screens.FilterScreen
import com.example.newsfeed.presentation.screens.NewsScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Routes.NEWS_SCREEN.name) {

        composable(Routes.NEWS_SCREEN.name) {
            NewsScreen(onNavigateNext = {navHostController.navigate(route = Routes.DETAILS.name)})
        }
        composable(Routes.FILTER.name) {
            FilterScreen()
        }
        composable(Routes.BOOKMARK.name) {
            BookmarkScreen()
        }
        composable(Routes.DETAILS.name) {
            DetailsScreen(onNavigateBack = {
                navHostController.navigate(Routes.NEWS_SCREEN.name)
            })
        }
      /*  composable(Routes.NETWORK.name){
            NetworkScreen()
        }*/
    }
}