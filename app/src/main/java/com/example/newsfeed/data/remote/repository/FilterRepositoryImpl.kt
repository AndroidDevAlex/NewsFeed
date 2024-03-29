package com.example.newsfeed.data.remote.repository

import com.example.newsfeed.data.remote.HabrServiceApi
import com.example.newsfeed.data.remote.RedditServiceApi
import com.example.newsfeed.domain.FilterRepository
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val redditServiceApi: RedditServiceApi,
    private val habrServiceApi: HabrServiceApi
) : FilterRepository {

}