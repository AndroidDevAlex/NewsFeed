package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsEntity
import com.example.newsfeed.data.remote.models.Author
import com.example.newsfeed.data.remote.models.Entry
import com.example.newsfeed.presentation.NewsUi

fun Entry.toNewsUi(): NewsUi {

    return NewsUi(
        id = id ?: -1,
        image = "",
        title = this.title ?: "no title",
        publishedAt = this.published ?: "",
        description = this.description ?: "no description",
        addedBy = "",
        isBookmarked = false,
        source = this.link ?: "no source"
    )
}

fun Entry.toNewsEntity(): NewsEntity {
    return NewsEntity(
        id = id,
        image = "",
        title = this.title ?: "no title",
        publishedAt = this.published ?: "",
        description = this.description ?: "no description",
        addedBy = "",
        isBookmarked = false,
        source = this.link ?: "no source"
    )
}

fun NewsUi.toNewsEntity(): NewsEntity {
    return NewsEntity(
        id = this.id,
        image = this.image ?: "",
        title = this.title ?: "",
        publishedAt = this.publishedAt ?: "",
        description = this.description ?: "",
        addedBy = this.addedBy ?: "",
        isBookmarked = this.isBookmarked,
        source = this.source ?: ""
    )
}

fun NewsEntity.fromDbToNewsUi(): NewsUi? {
    return id?.let {
        NewsUi(
            id = it,
            image = this.image ?: "",
            title = this.title ?: "",
            publishedAt = this.publishedAt ?: "",
            description = this.description ?: "",
            addedBy = this.addedBy ?: "",
            isBookmarked = this.isBookmarked,
            source = this.source ?: ""
        )
    }
}