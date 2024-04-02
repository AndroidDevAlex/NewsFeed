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
        val currentRoute = backStackEntry?.destination?.route ?: Routes.NEWS_SCREEN.name
        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route.name,
                onClick = {
                    navController.navigate(item.route.name)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon",
                        tint = if (currentRoute == item.route.name) Orange else Color.White
                    )
                },
                )
        }
    }
}