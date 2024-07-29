package com.example.newsfeed.domain.useCase.homeCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import javax.inject.Inject
import javax.inject.Named

class FetchNewsUseCase @Inject constructor(
    @Named("Habr") private val habrRepository: NewsRepository,
    @Named("Reddit") private val redditRepository: NewsRepository
) {

    suspend fun refreshNews() {
        habrRepository.fetchAndSaveNews()
        redditRepository.fetchAndSaveNews()
    }
}