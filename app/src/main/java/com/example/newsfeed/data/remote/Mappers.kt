package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsDB
import com.example.newsfeed.data.remote.models.Entry
import com.example.newsfeed.data.remote.models.ResponseNews
import com.example.newsfeed.presentation.NewsUi

fun Entry.mapToUi(): NewsUi {

    val descriptionValue = description?.value ?: ""
    val sourceValue = link?.href ?: ""

    return NewsUi(
        id = id,
        image = "",
        title = this.title ?: "",
        publishedAt = this.published ?: "",
        description = descriptionValue,
        addedBy = "",
        isBookmarked = false,
        source = sourceValue
    )
}

fun ResponseNews.mapToUiFromResponseNews(): NewsUi {

    val entriesDescriptions = entries?.map { it.description?.value ?: "" } ?: listOf("")
    val entriesPublishedAt = entries?.map { it.published ?: "" } ?: listOf("")

    return NewsUi(
        id = id,
        image = "",
        title = this.title ?: "",
        publishedAt = entriesPublishedAt.joinToString { "," },
        description = entriesDescriptions.joinToString(", "),
        addedBy = "",
        isBookmarked = false,
        source = link?.href ?: ""
    )
}

fun NewsUi.mapToDB(): NewsDB {
    return NewsDB(
        id = id,
        image = this.image,
        title = this.title,
        publishedAt = this.publishedAt,
        description = this.description ?: "",
        addedBy = this.addedBy,
        isBookmarked = this.isBookmarked,
        source = this.source ?: ""
    )
}

fun NewsDB.mapFromDBToUi(): NewsUi {
    return NewsUi(
        id = id,
        image = this.image,
        title = this.title,
        publishedAt = this.publishedAt,
        description = this.description,
        addedBy = this.addedBy,
        isBookmarked = this.isBookmarked,
        source = this.source
    )
}

fun Entry.mapToDB(): NewsDB {

    val contentValue = description?.value ?: ""
    val sourceValue = link?.href ?: ""

    return NewsDB(
        id = id,
        image = "",
        title = this.title ?: "",
        publishedAt = this.published ?: "",
        description = contentValue,
        addedBy = "",
        isBookmarked = false,
        source = sourceValue
    )
}