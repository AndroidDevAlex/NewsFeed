package com.example.newsfeed.navigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsfeed.ui.theme.Blue
import com.example.newsfeed.ui.theme.DarkGray

@Composable
fun BottomNavigation(
    navController: NavController
) {
    val listItems = listOf(
        BottomItem.NewsScreen,
        BottomItem.Filter,
        BottomItem.Bookmark
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = DarkGray
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route ?: Screen.Home.route
        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon",
                        tint = if (currentRoute == item.screen.route) Blue else Color.White
                    )
                }
            )
        }
    }
}