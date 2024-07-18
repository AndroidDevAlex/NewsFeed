package com.example.newsfeed.util

import android.content.Context
import androidx.navigation.NavController
import com.example.newsfeed.navigation.Screen
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import java.net.URLEncoder

fun navigateAndCheckIsConnected(
    navController: NavController,
    news: ItemNewsUi,
    isConnected: Boolean,
    context: Context
) {
    if (isConnected) {
        val encodedUrl = URLEncoder.encode(news.url, "UTF-8")
        navController.navigate(Screen.Details.route + "/$encodedUrl")
    } else {
        showNoInternetToast(context)
    }
}
