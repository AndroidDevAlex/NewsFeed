package com.example.newsfeed.data.remote.repository

import android.util.Log
import com.example.newsfeed.Logger
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.HabrServiceApi
import com.example.newsfeed.data.remote.RedditServiceApi
import com.example.newsfeed.data.remote.mapToDB
import com.example.newsfeed.data.remote.mapToUiFromNewsFeed
import com.example.newsfeed.data.remote.mapToUiFromResponseNews
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    private val habrServiceApi: HabrServiceApi,
    private val redditServiceApi: RedditServiceApi,
    private val newsDao: NewsDao,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val logger: Logger
) : NewsRepository {

    override fun getOllNewsList(): Flow<RequestResult<List<NewsUi>>> {
        val newsList = mutableListOf<NewsUi>()

        val remoteHabrFlow = flow {
            emit(RequestResult.Loading(true))
            try {
                val result = habrServiceApi.getAllHabrNews()//getHabrNews()
                if (result.isSuccessful) {
                    emit(
                        RequestResult.Success(
                            result.body()?.mapToUiFromNewsFeed() ?: newsList
                        )
                    )
                } else {
                    emit(RequestResult.Error())
                    throw IOException("Error fetching Habr news")
                }
            } catch (e: Exception) {
                logger.e(LOG_TAG, "Error fetching Habr news: $e")
            } finally {
                emit(RequestResult.Loading(false))
            }
        }
        val remoteRedditFlow = flow {
            emit(RequestResult.Loading(true))
            try {
                val result = redditServiceApi.getRedditNews()
                if (result.isSuccessful) {
                    emit(
                        RequestResult.Success(
                            result.body()?.mapToUiFromResponseNews() ?: newsList
                        )
                    )
                } else {
                    emit(RequestResult.Error())
                    throw IOException("Error fetching Reddit news")
                }
            } catch (e: Exception) {
                logger.e(LOG_TAG, "Error fetching Reddit news: $e")
            } finally {
                emit(RequestResult.Loading(false))
            }
        }
        return remoteHabrFlow.combine(remoteRedditFlow) { habrNews, redditNews ->
            when {
                habrNews is RequestResult.Success && redditNews is RequestResult.Success -> {
                    val combinedList = mutableListOf<NewsUi>()
                    combinedList.addAll(habrNews.data as List<NewsUi>)
                    combinedList.addAll(redditNews.data as List<NewsUi>)
                    RequestResult.Success(combinedList)
                }

                habrNews is RequestResult.Error || redditNews is RequestResult.Error -> {
                    RequestResult.Error()
                }

                else -> {
                    RequestResult.Loading(true)
                }
            }
        }
    }

    override fun getNewsById(id: Int, source: String): Flow<RequestResult<NewsUi>> {
        TODO("Not yet implemented")
    }

   /*   override fun getNewsById(id: Int, source: String): Flow<RequestResult<NewsUi>> {

          return flow {
              runCatching {
                  emit(RequestResult.Loading(true))

                  val newsResponse = when (source) {
                      "habr" -> habrServiceApi.getHabrNewsById(id.toString())
                      else -> redditServiceApi.getRedditNewsById(id)
                  }

                  if (newsResponse.isSuccessful) {
                      val news = newsResponse.body()
                      if (news != null) {
                          val newsModel = news.

                          emit(RequestResult.Success(newsModel))
                      } else {

                          emit(RequestResult.Error())
                      }

                  } else {
                      emit(RequestResult.Error())
                  }
                  emit(RequestResult.Loading(false))
              }.onFailure { e ->
                  logger.e(LOG_TAG, "Error fetching news from server = $e")
                  emit(RequestResult.Error())
              }
          }
      }*/

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

    override suspend fun fetchLatest(): List<NewsUi> {

        return getOllNewsList()
            .map { result ->
                when (result) {
                    is RequestResult.Success -> result.data ?: emptyList()
                    else -> emptyList()
                }
            }
            .first()
    }

    override fun stateBookmark(id: Int): Flow<Boolean> {
        return newsDao.getNewsByIdFlow(id)
            .map { news -> news.isBookmarked }
    }

    private companion object {
        const val LOG_TAG = "NewsRepository"
    }
}