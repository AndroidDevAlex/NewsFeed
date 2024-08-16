package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.NewsUi
import com.example.newsfeed.util.NewsSource
import javax.inject.Inject

class HabrRepository @Inject constructor(
    private val apiManager: ApiManager
) : Sources {

    override suspend fun fetchNews(): NewsUi {
        return apiManager.getAllHabrNewsList().apply {
            val defaultImage = this.defaultImage
            this.newsList.forEach { news ->
                if (news.image == null) {
                    news.image = defaultImage
                }
            }
        }
    }

    override fun getSourceName(): String {
        return NewsSource.HABR.sourceName
    }
}