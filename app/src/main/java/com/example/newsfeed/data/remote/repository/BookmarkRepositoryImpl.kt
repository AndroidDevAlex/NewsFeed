package com.example.newsfeed.data.remote.repository

import android.util.Log
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.local.NewsDB
import com.example.newsfeed.domain.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao
) : BookmarkRepository {

    override fun getSavedNewsFromLocalDB(): Flow<List<NewsDB>> {
        return flow {
            runCatching {
                newsDao.getAllNews()

            }.onFailure { e ->
                Log.e("error", " error occurred while receiving data $e")
            }
        }
    }
}