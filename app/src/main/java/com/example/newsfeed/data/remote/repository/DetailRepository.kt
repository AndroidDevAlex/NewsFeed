package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.presentation.entityUi.ItemNewsUi

interface DetailRepository {

    suspend fun toggleBookmark(news: ItemNewsUi)

    suspend fun getNewsByUrl(newsUrl: String): ItemNewsUi
}