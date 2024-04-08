package com.example.newsfeed.data.remote.repository

import android.util.Log
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.mapToDB
import com.example.newsfeed.domain.DetailRepository
import com.example.newsfeed.presentation.NewsUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class DetailRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : DetailRepository {

    override suspend fun saveNews(news: NewsUi) {
        runCatching {
            withContext(ioDispatcher) {
                val newsEntity = news.mapToDB()
                val updateLocalDB = newsDao.insertNews(newsEntity)
                if (updateLocalDB != -1L) {
                    Log.i("log", "News successfully saved in Local database")
                } else {
                    Log.e("log", "Error saving location data")
                }
            }
        }.onFailure { e ->
            Log.e("log", "Error in method saveNews", e)
        }
    }

    override suspend fun deleteNews(id: Int) {
        runCatching {
            withContext(ioDispatcher) {
                newsDao.deleteNewsById(id)
                Log.i("log", "News successfully removed from Room database")
            }
        }.onFailure { e ->
            Log.e("log", "error occurred while deleting data: ", e)
        }
    }
}