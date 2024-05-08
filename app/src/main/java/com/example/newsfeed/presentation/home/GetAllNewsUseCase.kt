package com.example.newsfeed.presentation.home


import com.example.newsfeed.domain.NewsRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

 suspend operator fun invoke(): Flow<RequestResult<List<NewsUi>>> {
     return flow { emit(repository.getOllNewsList())}
 }
}