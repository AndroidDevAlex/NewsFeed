package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.local.NewsDao
import com.example.newsfeed.domain.DetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class DetailRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : DetailRepository {

}