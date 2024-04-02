package com.example.newsfeed.navigation

import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsfeed.ui.theme.Orange

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
        backgroundColor = Color.Gray
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route ?: Screen.Home.route
        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon",
                        tint = if (currentRoute == item.screen.route) Orange else Color.White
                    )
                },
                )
        }
    }
}