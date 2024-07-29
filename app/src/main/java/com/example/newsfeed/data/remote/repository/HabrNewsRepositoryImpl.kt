package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.presentation.entityUi.NewsUi
import javax.inject.Inject

class HabrNewsRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource,
    private val apiManager: ApiManager
) : BaseNewsRepository(apiManager, dataSource) {

    override suspend fun fetchRemoteNews(): NewsUi {
        return apiManager.getAllHabrNewsList().apply {
            val defaultImage = this.defaultImage
            this.newsList.forEach { news ->
                if (news.image == null) {
                    news.image = defaultImage
                }
            }
        }
    }
}