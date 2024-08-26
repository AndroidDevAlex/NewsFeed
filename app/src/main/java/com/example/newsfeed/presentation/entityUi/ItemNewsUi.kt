package com.example.newsfeed.presentation.entityUi

data class ItemNewsUi(
    val id: String,
    val image: String,
    val title: String,
    val publishedAt: String,
    val description: String,
    val addedBy: String,
    val isBookmarked: Boolean,
    val source: String,
    val url: String,
    val timeStamp: Long
)