package com.example.newsfeed.domain

import com.example.newsfeed.presentation.entityUi.ItemNewsUi

interface DetailRepository {

    suspend fun toggleBookmark(news: ItemNewsUi)

    suspend fun getNewsByUrl(newsUrl: String): ItemNewsUi
}