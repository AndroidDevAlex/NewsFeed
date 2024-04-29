package com.example.newsfeed.data.remote.repository


import android.util.Log
import com.example.newsfeed.Logger
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.HabrServiceApi
import com.example.newsfeed.data.remote.RedditServiceApi
import com.example.newsfeed.data.remote.mapToDB
import com.example.newsfeed.data.remote.mapToNewsUi
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    private val habrServiceApi: HabrServiceApi,
    private val redditServiceApi: RedditServiceApi,
    private val newsDao: NewsDao,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val logger: Logger
) : NewsRepository {

    override suspend fun getOllNewsList(): RequestResult<List<NewsUi>> {
       return try {
            val result = habrServiceApi.getAllHabrNews()
            if (result.isSuccessful) {
                RequestResult.Success(
                   result.body()?.channel?.items?.mapToNewsUi() ?: mutableListOf()
                )
            } else {
                RequestResult.Error()
            }
        } catch (e: Exception) {
            logger.e("log", "Error fetching Habr news: $e")
            RequestResult.Error()
        }
    }

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