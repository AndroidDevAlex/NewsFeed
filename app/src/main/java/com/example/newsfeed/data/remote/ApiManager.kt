package com.example.newsfeed.data.remote


import com.example.newsfeed.presentation.NewsUi

class ApiManager(
    private val habrServiceApi: HabrServiceApi,
    private val redditServiceApi: RedditServiceApi
) {

    suspend fun getOllNewsList(): List<NewsUi> {
        val result = habrServiceApi.getAllHabrNews()
        return result.channel.items.mapToNewsUi()
    }
}