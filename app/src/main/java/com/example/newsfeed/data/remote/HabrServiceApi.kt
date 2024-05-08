package com.example.newsfeed.data.remote


import com.example.newsfeed.data.remote.models.habrModels.NewsFeed
import retrofit2.http.GET

interface HabrServiceApi {

    @GET("ru/rss/articles/?fl=ru")
    suspend fun getAllHabrNews(): NewsFeed
}