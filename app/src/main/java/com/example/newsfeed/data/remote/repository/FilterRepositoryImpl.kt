package com.example.newsfeed.data.remote.repository

import android.util.Log
import com.example.newsfeed.data.remote.HabrServiceApi
import com.example.newsfeed.data.remote.News
import com.example.newsfeed.data.remote.RedditServiceApi
import com.example.newsfeed.domain.repository.FilterRepository
import com.example.newsfeed.util.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val redditServiceApi: RedditServiceApi,
    private val habrServiceApi: HabrServiceApi
) : FilterRepository {

    override suspend fun getRedditNews(): Flow<List<ResourceState<News>>> {
        return flow {
            runCatching {

                emit(listOf(ResourceState.Loading(true)))
                val response = redditServiceApi.getRedditNews()

                if (response.isSuccessful && response.body() != null) {
                    emit(listOf(ResourceState.Success(response.body()!!)))
                } else {
                    emit(listOf(ResourceState.Error("error fetching news from Reddit")))
                }
                emit(listOf(ResourceState.Loading(false)))
            }.onFailure { e ->
                Log.e("tag", "Error fetching news: $e")

                emit(listOf(ResourceState.Error("Error fetching news: $e")))
            }
        }
    }
}