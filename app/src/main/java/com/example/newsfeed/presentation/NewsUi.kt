package com.example.newsfeed.presentation

data class NewsUi(
    val id: Int,
    val image: String?,
    val title: String,
    val publishedAt: String,
    val description: String,
    val addedBy: String,
    val isBookmarked: Boolean,
    val source: String
)