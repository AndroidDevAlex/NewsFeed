package com.example.newsfeed.data.remote.repository

import android.util.Log
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.HabrServiceApi
import com.example.newsfeed.data.remote.RedditServiceApi
import com.example.newsfeed.data.remote.mapToDB
import com.example.newsfeed.data.remote.mapToUi
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    private val habrServiceApi: HabrServiceApi,
    private val redditServiceApi: RedditServiceApi,
    private val newsDao: NewsDao,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : NewsRepository {

    override fun getOllNewsList(): Flow<RequestResult<List<NewsUi>>> {
        return flow {

            emit(RequestResult.Loading(true))

            runCatching {
                val newsList = mutableListOf<NewsUi>()

                val habrNewsResponse = habrServiceApi.getHabrNews()
                val redditNewsResponse = redditServiceApi.getRedditNews()

                if (habrNewsResponse.isSuccessful && redditNewsResponse.isSuccessful) {
                    val habrNews = habrNewsResponse.body()
                    val redditNews = redditNewsResponse.body()

                    habrNews?.let { newsList.addAll(listOf(it.mapToUi())) }
                    redditNews?.let { newsList.addAll(listOf(it.mapToUi())) }

                    emit(RequestResult.Success(newsList))
                } else if (habrNewsResponse.isSuccessful) {
                    habrNewsResponse.body()?.let { newsList.addAll(listOf(it.mapToUi())) }
                    emit(RequestResult.Success(newsList))
                } else if (redditNewsResponse.isSuccessful) {
                    redditNewsResponse.body()?.let { newsList.addAll(listOf(it.mapToUi())) }
                    emit(RequestResult.Success(newsList))
                } else {
                    emit(RequestResult.Error())
                }

                emit(RequestResult.Loading(false))
            }.onFailure { e ->
                Log.e("error", "$e")
            }
        }
    }

    override fun getNewsById(id: Int, source: String): Flow<RequestResult<NewsUi>> {

        return flow {
            runCatching {
                emit(RequestResult.Loading(true))

                val newsResponse = when (source) {
                    "habr" -> habrServiceApi.getHabrNewsById(id)
                    else -> redditServiceApi.getRedditNewsById(id)
                }

                if (newsResponse.isSuccessful) {
                    val news = newsResponse.body()
                    if (news != null) {

                        val newsModel = news.id?.let {
                            NewsUi(
                                id = it,
                                image = "",
                                title = news.title ?: "",
                                publishedAt = news.published ?: "",
                                description = news.description ?: "",
                                addedBy = news.authorBy?.name ?: "",
                                isBookmarked = false,
                                source = news.link ?: ""
                            )
                        }

                        emit(RequestResult.Success(newsModel))
                    } else {

                        emit(RequestResult.Error())
                    }

                } else {
                    emit(RequestResult.Error())
                }
                emit(RequestResult.Loading(false))
            }.onFailure { e ->
                Log.e("tag", "Error fetching news: $e")

                emit(RequestResult.Error())
            }
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
}