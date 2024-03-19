package com.example.newsfeed.data.remote.repository

import android.util.Log
import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.data.remote.News
import com.example.newsfeed.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao
) : BookmarkRepository {

    override fun getSavedNewsFromLocalDB(): Flow<List<News>> {
        return flow {
            runCatching {
                newsDao.getAllNews()

            }.onFailure { e ->
                Log.e("error", " error occurred while receiving data $e")
            }
        }
    }
}