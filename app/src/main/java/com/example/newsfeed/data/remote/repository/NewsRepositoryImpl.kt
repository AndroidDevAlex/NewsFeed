package com.example.newsfeed.data.remote.repository

import android.util.Log
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.local.NewsEntity
import com.example.newsfeed.data.remote.HabrServiceApi
import com.example.newsfeed.data.remote.RedditServiceApi
import com.example.newsfeed.util.ResourceState
import com.example.newsfeed.data.remote.News
import com.example.newsfeed.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class NewsRepositoryImpl @Inject constructor(
    private val habrServiceApi: HabrServiceApi,
    private val redditServiceApi: RedditServiceApi,
    private val newsDao: NewsDao,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : NewsRepository {

    override fun getNewsList(sources: List<String>): Flow<ResourceState<List<News>>> {
        return flow {
            runCatching {
                emit(ResourceState.Loading(true))

                val newsList = mutableListOf<News>()

                sources.forEach { source ->
                    when (source) {
                        "habr" -> {
                            val habrNewsResponse = habrServiceApi.getHabrNews()
                            if (habrNewsResponse.isSuccessful) {
                                val updateHabr = habrNewsResponse.body()

                                if (updateHabr != null) {
                                    val newsHabrModel = News(
                                        id = updateHabr.id,
                                        image = updateHabr.image,
                                        title = updateHabr.title,
                                        publishedAt = updateHabr.publishedAt,
                                        description = "",
                                        addedBy = "",
                                        isBookmarked = false,
                                        source = updateHabr.source
                                    )
                                  //  newsList.addAll(listOf(newsHabrModel))
                                }
                               // newsList.addAll()
                                emit(ResourceState.Success(newsList))

                            } else {
                                emit(ResourceState.Error("Error fetching news data"))
                            }
                        }

                        "reddit" -> {
                            val redditNewsResponse = redditServiceApi.getRedditNews()
                            if (redditNewsResponse.isSuccessful) {
                                val updateReddit = redditNewsResponse.body()
                                if (updateReddit != null) {

                                    val newsRedditModel = News(
                                        id = updateReddit.id,
                                        image = updateReddit.image,
                                        title = updateReddit.title,
                                        publishedAt = updateReddit.publishedAt,
                                        description = "",
                                        addedBy = "",
                                        isBookmarked = false,
                                        source = updateReddit.source
                                    )
                                   // newsList.addAll(listOf(newsRedditModel))
                                }

                                //newsList.addAll()

                                emit(ResourceState.Success(newsList))

                            } else {
                                emit(ResourceState.Error("Error fetching news data"))
                            }
                        }
                    }

                }
                emit(ResourceState.Loading(false))

            }.onFailure { e ->
                Log.e("error", "$e")
            }
        }
    }

    override fun getNews(id: Int, source: String): Flow<ResourceState<News>> {
        return flow {
            runCatching {

                val newsResponse = when (source) {
                    "habr" -> habrServiceApi.getHabrNewsById(id)
                    else -> redditServiceApi.getRedditNewsById(id)
                }

                if (newsResponse.isSuccessful) {
                    val news = newsResponse.body()

                    if (news != null) {
                        val newsModel = News(
                            id = news.id,
                            image = news.image,
                            title = news.title,
                            publishedAt = news.publishedAt,
                            description = news.description,
                            addedBy = news.addedBy,
                            isBookmarked = false,
                            source = news.source
                        )

                        emit(ResourceState.Success(newsModel))
                    } else {

                        emit(ResourceState.Error("No news data available"))
                    }
                } else {

                    emit(ResourceState.Error("Failed to fetch news from $source"))
                }
            }.onFailure { e ->
                Log.e("tag", "Error fetching news: $e")

                emit(ResourceState.Error("Error fetching news: $e"))
            }
        }
    }

    override suspend fun saveNews(news: News) {
        runCatching {
            val newsEntity = NewsEntity(
                id = news.id,
                image = news.image,
                title = news.title,
                publishedAt = news.publishedAt,
                description = news.description,
                addedBy = news.addedBy,
                isBookmarked = news.isBookmarked,
                source = news.source
            )
            val updateLocalDB = newsDao.insertNews(newsEntity)
            if (updateLocalDB != -1L) {
                Log.i("log", "News successfully saved in Local database")
            } else {
                Log.e("log", "Error saving location data")
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