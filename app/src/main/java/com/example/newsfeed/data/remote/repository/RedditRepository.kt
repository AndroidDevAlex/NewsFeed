package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.NewsUi
import com.example.newsfeed.util.NewsSource
import javax.inject.Inject

class RedditRepository @Inject constructor(
    private val apiManager: ApiManager
) : Sources {

    override suspend fun fetchNews(): NewsUi {
        return apiManager.getAllRedditNewsList()
    }

    override fun getSourceName(): String {
        return NewsSource.REDDIT.sourceName
    }
}