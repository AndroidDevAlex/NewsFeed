package com.example.newsfeed.domain.useCase.homeCase

import com.example.newsfeed.data.remote.repository.NewsRepository
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import com.example.newsfeed.util.NewsSource
import javax.inject.Inject
import javax.inject.Named

class ToggleBookmarkUseCase @Inject constructor(
    @Named("Habr") private val habrRepository: NewsRepository,
    @Named("Reddit") private val redditRepository: NewsRepository
) {

    suspend fun toggleBookmark(news: ItemNewsUi) {
        if (news.source == NewsSource.HABR.sourceName) {
            habrRepository.toggleBookmark(news)
        } else if (news.source == NewsSource.REDDIT.sourceName) {
            redditRepository.toggleBookmark(news)
        }
    }
}