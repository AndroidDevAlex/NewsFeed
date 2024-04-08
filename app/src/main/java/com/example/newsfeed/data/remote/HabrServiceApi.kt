package com.example.newsfeed.data.remote

import com.example.newsfeed.data.remote.models.Entry
import com.example.newsfeed.data.remote.models.ResponseNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HabrServiceApi {

    @GET("ru/docs/help/lenta/")
    suspend fun getHabrNews():  Response<ResponseNews>

    @GET("ru/docs/help/lenta/{id}")
    suspend fun getHabrNewsById(@Path("id") id: Int): Response<Entry>
}