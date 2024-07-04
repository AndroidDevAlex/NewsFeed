package com.example.newsfeed.data.remote


import com.example.newsfeed.data.remote.models.redditModels.NewsFeedReddit
import retrofit2.http.GET

interface RedditServiceApi {

    @GET("r/news/.rss")
    suspend fun getOllRedditNews(): NewsFeedReddit
}