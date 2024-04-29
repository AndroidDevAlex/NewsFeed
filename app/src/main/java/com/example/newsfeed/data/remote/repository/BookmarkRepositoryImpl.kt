package com.example.newsfeed.data.remote.repository

import android.util.Log
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.mapFromDBToUi
import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao
) : BookmarkRepository {

    override fun getSavedNews(): Flow<List<NewsUi>> {
        return flow {
            runCatching {
                 newsDao.getAllNews().map {newsDB ->
                 newsDB.mapFromDBToUi()
                }
            }.onFailure { e ->
                Log.e("error", " error occurred while receiving data $e")
            }
        }
    }

   /* override suspend fun getSavedNews(): List<NewsDB> = withContext(ioDispatcher){
        return@withContext newsDao.getAllNews()
    }*/
}