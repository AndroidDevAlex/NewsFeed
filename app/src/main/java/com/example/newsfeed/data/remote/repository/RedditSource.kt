package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.NewsUi
import javax.inject.Inject

class RedditSource @Inject constructor(
    private val apiManager: ApiManager
) : Source {

    override suspend fun fetchNews(): NewsUi {
        return apiManager.getAllRedditNewsList()
    }

    override fun getSourceName(): String {
        return REDDIT
    }

    companion object {
        private const val REDDIT = "reddit"
    }
}