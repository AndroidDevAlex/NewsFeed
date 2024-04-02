package com.example.newsfeed.domain

import com.example.newsfeed.data.local.NewsDB
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    fun getSavedNewsFromLocalDB(): Flow<List<NewsDB>>
}