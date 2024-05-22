package com.example.newsfeed.data.remote.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.remote.ApiManager
import com.example.newsfeed.data.remote.RoomDataSource
import com.example.newsfeed.data.remote.mapFromDBToUi
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource,
    private val apiManager: ApiManager
) : NewsRepository {

    /*override suspend fun getSavedNews(): List<NewsUi> {
        return dataSource.getSavedNews()
    }*/

    /*override fun getSavedNewsPagingData(): Flow<PagingData<NewsUi>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { dataSource.getPagginSavedNews()}
        ).flow.map { pagingData ->
            pagingData.map { it.mapFromDBToUi() }
        }
    }*/

    override fun getSavedNewsPagingSource(): Flow<PagingData<NewsUi>> {
        return dataSource.getPagingSavedNews().map { pagingDB ->
            pagingDB.map { it.mapFromDBToUi() }
        }
    }

    override suspend fun fetchAndSaveNews(): RequestResult<List<NewsUi>> {

        val remoteNews = apiManager.getOllNewsList()

        remoteNews.data?.forEach { news ->
            dataSource.saveNews(news)
        }
        return remoteNews
    }

    override suspend fun saveNews(news: NewsUi) {
        dataSource.saveNews(news)
    }

    override suspend fun deleteNews(news: NewsUi) {
        dataSource.deleteNews(news)
    }
}