package com.example.newsfeed.data.remote.api.redditApi


import com.example.newsfeed.data.remote.models.redditModels.NewsFeedReddit
import retrofit2.http.GET

interface RedditServiceApi {

    @GET("r/news/.rss")
    suspend fun getOllRedditNews(): NewsFeedReddit
}