package com.example.newsfeed.data.remote.api


import com.example.newsfeed.data.remote.api.habrApi.HabrServiceApi
import com.example.newsfeed.data.remote.api.redditApi.RedditServiceApi
import com.example.newsfeed.presentation.entityUi.NewsUi
import com.example.newsfeed.util.mapToNewsUi
import com.example.newsfeed.util.mapToNewsUiReddit

class ApiManager(
    private val habrServiceApi: HabrServiceApi,
    private val redditServiceApi: RedditServiceApi
) {

    suspend fun getAllHabrNewsList(): NewsUi {
        return habrServiceApi.getAllHabrNews().mapToNewsUi()
    }

    suspend fun getAllRedditNewsList(): NewsUi {
        return redditServiceApi.getOllRedditNews().mapToNewsUiReddit()
    }
}