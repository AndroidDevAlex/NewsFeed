package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.remote.api.ApiManager
import com.example.newsfeed.presentation.entityUi.NewsUi
import javax.inject.Inject

class HabrSource @Inject constructor(
    private val apiManager: ApiManager
) : Source {

    override suspend fun fetchNews(): NewsUi {
        return apiManager.getAllHabrNewsList()
    }

    override fun getSourceName(): String {
        return HABR
    }

    companion object {
        private const val HABR = "habr"
    }
}