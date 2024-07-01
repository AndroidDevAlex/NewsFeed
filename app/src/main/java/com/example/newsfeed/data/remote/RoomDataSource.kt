package com.example.newsfeed.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.presentation.entityUi.ItemNewsUi

class RoomDataSource(
    private val newsDao: NewsDao
) {

    fun getOllPagingSavedNews() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = INITIAL_LOAD_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        ),
        pagingSourceFactory = { newsDao.getAllSavedNews() }
    ).flow

    fun getOllPagingSavedNewsByUser() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = INITIAL_LOAD_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        ),
        pagingSourceFactory = { newsDao.getSavedNewsPaginationByUser() }
    ).flow

    suspend fun updateBookmarkStatus(news: ItemNewsUi) {
        newsDao.insertNews(news.mapToDB(news.isBookmarked))
    }

    suspend fun getNewsById(newsId: Long): ItemNewsUi? {
        return newsDao.getNewsById(newsId)?.mapFromDBToUi()
    }

    suspend fun getNewsByUrl(newsUrl: String): ItemNewsUi {
        return newsDao.getNewsByUrl(newsUrl).mapFromDBToUi()
    }

    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_LOAD_SIZE = 40
        const val PREFETCH_DISTANCE = 10
    }
}