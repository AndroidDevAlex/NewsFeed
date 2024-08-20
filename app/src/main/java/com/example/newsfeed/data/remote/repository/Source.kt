package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.presentation.entityUi.NewsUi

interface Source {

    suspend fun fetchNews(): NewsUi

    fun getSourceName(): String
}