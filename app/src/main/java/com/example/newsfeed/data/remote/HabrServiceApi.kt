package com.example.newsfeed.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HabrServiceApi {

    @GET("ru/docs/help/lenta/")
    suspend fun getHabrNews(): Response<News>

    @GET("ru/docs/help/lenta/{id}")
    suspend fun getHabrNewsById(@Path("id") id: Int): Response<News>
}