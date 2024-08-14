package com.example.newsfeed.domain.useCase.homeCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.util.NewsSource
import javax.inject.Inject

class FetchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
){
    suspend fun fetchNews(sources: List<NewsSource>) {
        newsRepository.fetchAndSaveNews(sources)
    }
}