package com.example.newsfeed.data.remote.repository

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsfeed.data.remote.ApiManager
import com.example.newsfeed.data.remote.RoomDataSource
import com.example.newsfeed.data.remote.mapFromDBToUi
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val dataSource: RoomDataSource,
    private val apiManager: ApiManager
) : NewsRepository {

    override fun getSavedNewsPagingSource(): Flow<PagingData<NewsUi>> {
        return dataSource.getPagingSavedNews().map { pagingDB ->
            pagingDB.map { it.mapFromDBToUi() }
        }
    }

   override suspend fun fetchAndSaveNews() {
       try {
           val remoteNews = apiManager.getOllNewsList()
           remoteNews.forEach { news ->
               dataSource.saveNews(news)
           }
       } catch (e: Exception) {
           Log.e("log", "Error during fetch and save news: $e")
           throw e
       }
   }

    override suspend fun saveNews(news: NewsUi) {
        dataSource.saveNews(news)
    }

    override suspend fun deleteNews(news: NewsUi) {
        dataSource.deleteNews(news)
    }
}