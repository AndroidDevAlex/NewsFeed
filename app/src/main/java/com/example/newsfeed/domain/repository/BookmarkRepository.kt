package com.example.newsfeed.domain.repository

import com.example.newsfeed.data.remote.News
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun getSavedNewsFromLocalDB(): Flow<List<News>>
}