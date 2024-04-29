package com.example.newsfeed.presentation.home


import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named

class GetAllNewsUseCase @Inject constructor(
    private val repository: NewsRepository,
    @Named("DefaultDispatcher") private val defDispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<RequestResult<List<NewsUi>>> {
        return flow { emit(repository.getOllNewsList())}.flowOn(defDispatcher)
    }
}