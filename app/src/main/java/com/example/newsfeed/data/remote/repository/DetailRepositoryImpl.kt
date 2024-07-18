package com.example.newsfeed.data.remote.repository


import com.example.newsfeed.data.local.RoomDataSource
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource
) : DetailRepository {

    override suspend fun toggleBookmark(news: ItemNewsUi) {
        dataSource.updateBookmarkStatus(news)
    }

    override suspend fun getNewsByUrl(newsUrl: String): ItemNewsUi {
        return dataSource.getNewsByUrl(newsUrl)
    }
}