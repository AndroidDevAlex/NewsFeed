package com.example.newsfeed.domain

import com.example.newsfeed.data.local.NewsEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun getSavedNewsFromLocalDB(): Flow<List<NewsEntity>>
}