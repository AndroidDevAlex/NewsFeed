package com.example.newsfeed.domain.repository

import com.example.newsfeed.data.remote.News
import com.example.newsfeed.util.ResourceState
import kotlinx.coroutines.flow.Flow

interface FilterRepository {

    suspend fun getRedditNews(): Flow<List<ResourceState<News>>>
}