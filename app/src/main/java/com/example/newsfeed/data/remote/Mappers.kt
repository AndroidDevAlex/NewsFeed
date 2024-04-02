package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsDB
import com.example.newsfeed.data.remote.models.Entry
import com.example.newsfeed.presentation.NewsUi

fun Entry.mapToUi(): NewsUi {

    return NewsUi(
        id = id,
        image = "",
        title = this.title ?: "",
        publishedAt = this.published ?: "",
        description = this.description ?: "",
        addedBy = "",
        isBookmarked = false,
        source = this.link ?: ""
    )
}

fun Entry.mapToDB(): NewsDB {
    return NewsDB(
        id = id,
        image = "",
        title = this.title ?: "",
        publishedAt = this.published ?: "",
        description = this.description ?: "",
        addedBy = "",
        isBookmarked = false,
        source = this.link ?: ""
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