package com.example.newsfeed.data.remote

import android.annotation.SuppressLint
import android.util.Log
import com.example.newsfeed.presentation.NewsUi

class ApiManager(
     private val habrServiceApi: HabrServiceApi,
     private val redditServiceApi: RedditServiceApi
) {

    @SuppressLint("SuspiciousIndentation")
    suspend fun getOllNewsList(): List<NewsUi> {
        return try {
            val result = habrServiceApi.getAllHabrNews()
                result.channel.items.mapToNewsUi()
        }catch (e: Exception){
            Log.e("log", "Error fetching Habr news: $e")
           // emptyList()
            throw e
        }
    }
}