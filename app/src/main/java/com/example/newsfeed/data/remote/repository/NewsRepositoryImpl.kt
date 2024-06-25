package com.example.newsfeed.data.remote.repository


import androidx.paging.PagingData
import com.example.newsfeed.data.remote.ApiManager
import com.example.newsfeed.data.remote.RoomDataSource
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource,
    private val apiManager: ApiManager
) : NewsRepository {

    override fun getSavedNewsPagingSource(): Flow<PagingData<ItemNewsUi>> {
        return dataSource.getOllPagingSavedNews()
    }

    override suspend fun fetchAndSaveNews() {
        val remoteNews = apiManager.getOllNewsList()
        val defaultImageUrl = remoteNews.defaultImage

        remoteNews.newsList.forEach { news ->
            val existingNews = dataSource.getNewsById(news.id)
            val isBookmarked = existingNews?.isBookmarked ?: false
            val newsForSave = news.copy(image = news.image ?: defaultImageUrl, isBookmarked = isBookmarked)
            dataSource.updateBookmarkStatus(newsForSave)
        }
    }

    override suspend fun toggleBookmark(news: ItemNewsUi) {
        dataSource.updateBookmarkStatus(news)
    }
}