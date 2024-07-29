package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.NewsUi
import javax.inject.Inject

class RedditNewsRepositoryImpl @Inject constructor(
    private val apiManager: ApiManager,
    private val dataSource: RoomDataSource
) : BaseNewsRepository(apiManager, dataSource) {

    override suspend fun fetchRemoteNews(): NewsUi {
        return apiManager.getAllRedditNewsList()
    }
}