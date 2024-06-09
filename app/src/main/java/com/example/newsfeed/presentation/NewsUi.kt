package com.example.newsfeed.presentation

data class NewsUi(
    val id: Long,
    val image: String?,
    val title: String,
    val publishedAt: String,
    val description: String,
    val addedBy: String,
    val isBookmarked: Boolean,
    val source: String,
    val url: String
)