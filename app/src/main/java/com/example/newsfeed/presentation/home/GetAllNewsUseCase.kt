package com.example.newsfeed.presentation.home


import androidx.paging.PagingData
import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    fun getSavedNewsPaging(): Flow<PagingData<NewsUi>> {
        return repository.getSavedNewsPagingSource()
    }

     /*suspend fun getSavedNews(): Flow<List<NewsUi>> {
        return flow {
            val savedNews = repository.getSavedNews()
            emit(savedNews)
        }
    }*/

    suspend fun fetchNews(): Flow<RequestResult<List<NewsUi>>> {
        return flow {
            val result = repository.fetchAndSaveNews()
            emit(result)
        }
    }
}