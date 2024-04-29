package com.example.newsfeed.presentation.bookmark

import com.example.newsfeed.domain.BookmarkRepository
import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named
/*
class GetAllSavedNews @Inject constructor(
    private val repository: BookmarkRepository,
    @Named("DefaultDispatcher") private val defDispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<List<NewsUi>> {
    return repository.getSavedNews().flowOn(defDispatcher)
    }

}*/