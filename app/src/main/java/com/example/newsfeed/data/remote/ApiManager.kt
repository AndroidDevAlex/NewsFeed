package com.example.newsfeed.data.remote


import com.example.newsfeed.presentation.entityUi.NewsUi

class ApiManager(
    private val habrServiceApi: HabrServiceApi,
    private val redditServiceApi: RedditServiceApi
) {

    suspend fun getOllNewsList(): NewsUi {
        return habrServiceApi.getAllHabrNews().mapToNewsUi()
    }
}