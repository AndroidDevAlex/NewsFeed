package com.example.newsfeed.domain.useCase.homeCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.util.NewsSource
import javax.inject.Inject
import javax.inject.Named

class FetchNewsUseCase @Inject constructor(
    @Named("Habr") private val habrRepository: NewsRepository,
    @Named("Reddit") private val redditRepository: NewsRepository
) {

    suspend fun fetchNews(sources: List<NewsSource>) {
        if (sources.isEmpty()) {
            habrRepository.fetchAndSaveNews()
            redditRepository.fetchAndSaveNews()
        } else {
            sources.forEach { source ->
                when (source) {
                    NewsSource.HABR -> habrRepository.fetchAndSaveNews()
                    NewsSource.REDDIT -> redditRepository.fetchAndSaveNews()
                    NewsSource.UNKNOWN -> TODO()
                }
            }
        }
    }
}