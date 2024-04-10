package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsDB
import com.example.newsfeed.data.remote.models.habrModels.Item
import com.example.newsfeed.data.remote.models.habrModels.NewsFeed
import com.example.newsfeed.data.remote.models.redditModels.Entry
import com.example.newsfeed.data.remote.models.redditModels.ResponseNews
import com.example.newsfeed.presentation.NewsUi

fun Entry.mapToUi(): NewsUi {

    val descriptionValue = description?.value ?: ""
    val sourceValue = link?.href ?: ""
    val  convertedId: Int? = id?.toIntOrNull()

    return NewsUi(
        id = convertedId,
        image = "",
        title = this.title ?: "",
        publishedAt = this.published ?: "",
        description = descriptionValue,
        addedBy = "",
        isBookmarked = false,
        source = sourceValue
    )
}

fun Item.mapFromItemToUi(): NewsUi {

    val id: Int? = link?.toIntOrNull()

    return NewsUi(
        id = id,
        image = "",
        title = this.title ?: "",
        publishedAt = this.pubDate,
        description = this.description,
        addedBy = "",
        isBookmarked = false,
        source = this.link ?: ""
    )
}

fun NewsFeed.mapToUiFromNewsFeed(): NewsUi {

    val firstItem = channel.items?.firstOrNull() ?: throw IllegalStateException("")
    val itemId = extractIdFromLink(firstItem.link ?: "")
    val published = channel.pubDate
    val convertedId: Int? = itemId.toIntOrNull()

    return NewsUi(
        id = convertedId,
        image = channel.image?.url,
        title = firstItem.title ?: "",
        publishedAt = published,
        description = firstItem.description ,
        addedBy = "",
        isBookmarked = false,
        source = channel.link ?: ""
    )
}

private fun extractIdFromLink(link: String): String {
    val equalSignIndex = link.lastIndexOf("=")

    return if (equalSignIndex != -1 && equalSignIndex < link.length - 1) {
        link.substring(equalSignIndex + 1)
    } else {
        ""
    }
}

fun ResponseNews.mapToUiFromResponseNews(): NewsUi {

    val entriesDescriptions = entries?.map { it.description?.value ?: "" } ?: listOf("")
    val entriesPublishedAt = entries?.map { it.published ?: "" } ?: listOf("")
    val convertedId: Int? = id?.toIntOrNull()

    return NewsUi(
        id = convertedId,
        image = this.image,
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
        id = id.toString().toInt(),
        image = this.image ?: "",
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

    val convertedId: Int? = id?.toIntOrNull()

    return NewsDB(
        id = convertedId,
        image = "",
        title = this.title ?: "",
        publishedAt = this.published ?: "",
        description = contentValue,
        addedBy = "",
        isBookmarked = false,
        source = sourceValue
    )
}