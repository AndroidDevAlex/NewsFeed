package com.example.newsfeed.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.presentation.NewsUi

class RoomDataSource(
    private val newsDao: NewsDao
) {

    fun getPagingSavedNews() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = INITIAL_LOAD_SIZE,
            prefetchDistance = PREFETCH_DISTANCE
        ),
        pagingSourceFactory = { newsDao.getNewsPagination() }
    ).flow


    suspend fun saveNews(news: NewsUi) {
        newsDao.insertNews(news.mapToDB())
    }

    suspend fun deleteNews(news: NewsUi) {
        newsDao.deleteNews(news.mapToDB())
    }

    suspend fun getNewsByUrl(newsUrl: String): NewsUi {
        return newsDao.getNewsByUrl(newsUrl).mapFromDBToUi()
    }

    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_LOAD_SIZE = 40
        const val PREFETCH_DISTANCE = 10
    }
}