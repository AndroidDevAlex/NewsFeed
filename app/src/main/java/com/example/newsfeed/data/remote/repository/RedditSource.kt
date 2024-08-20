package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.NewsUi
import com.example.newsfeed.util.ConstantsSourceNames
import javax.inject.Inject

class RedditSource @Inject constructor(
    private val apiManager: ApiManager
) : Source {

    override suspend fun fetchNews(): NewsUi {
        return apiManager.getAllRedditNewsList()
    }

    override fun getSourceName(): String {
        return ConstantsSourceNames.REDDIT
    }
}