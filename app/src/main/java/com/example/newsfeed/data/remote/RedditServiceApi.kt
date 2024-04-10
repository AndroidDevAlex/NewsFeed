package com.example.newsfeed.data.remote

import com.example.newsfeed.data.remote.models.redditModels.Entry
import com.example.newsfeed.data.remote.models.redditModels.ResponseNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditServiceApi {

    @GET("r/news/.rss/{id}")
    suspend fun getRedditNewsById(@Path("id") id: String): Response<Entry>

    @GET("r/news/.rss")
    suspend fun getRedditNews(): Response<ResponseNews>
}