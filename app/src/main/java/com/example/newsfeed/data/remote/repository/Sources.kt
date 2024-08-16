package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.presentation.entityUi.NewsUi

interface Sources {

    suspend fun fetchNews(): NewsUi

    fun getSourceName(): String
}