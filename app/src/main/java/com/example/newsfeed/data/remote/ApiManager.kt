package com.example.newsfeed.data.remote

import android.util.Log
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult

class ApiManager(
     private val habrServiceApi: HabrServiceApi,
     private val redditServiceApi: RedditServiceApi
) {

    suspend fun getOllNewsList(): RequestResult<List<NewsUi>> {
        return try {
            val result = habrServiceApi.getAllHabrNews()
            RequestResult.Success(
                result.channel.items.mapToNewsUi())
        }catch (e: Exception){
            Log.e("log", "Error fetching Habr news: $e")
            RequestResult.Error()
        }
    }
}