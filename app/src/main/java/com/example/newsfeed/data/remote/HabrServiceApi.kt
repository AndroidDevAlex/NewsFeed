package com.example.newsfeed.data.remote

import com.example.newsfeed.data.remote.models.habrModels.Item
import com.example.newsfeed.data.remote.models.habrModels.NewsFeed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HabrServiceApi {

    @GET("ru/rss/articles/?fl=ru")
    suspend fun getAllHabrNews(): Response<NewsFeed>

    @GET("ru/articles/{id}/")
    suspend fun getHabrNewsById(@Path("id") id: String): Response<Item>
}