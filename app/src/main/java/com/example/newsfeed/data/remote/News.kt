package com.example.newsfeed.data.remote

data class News(
    val id: Int,
    val image: String,
    val title: String,
    val publishedAt: Int,
    val description: String,
    val addedBy: String,
    val isBookmarked: Boolean,
    val source: String
)