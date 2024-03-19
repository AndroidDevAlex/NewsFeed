package com.example.newsfeed.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditServiceApi {

    @GET("wiki/rss/{id}")
    suspend fun getRedditNewsById(@Path("id") id: Int): Response<News>

    @GET("wiki/rss")
    suspend fun getRedditNews(): Response<News>
}