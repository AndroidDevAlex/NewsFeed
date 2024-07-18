package com.example.newsfeed.util

import com.example.newsfeed.data.local.NewsDB
import com.example.newsfeed.data.remote.models.habrModels.Item
import com.example.newsfeed.data.remote.models.habrModels.NewsFeed
import com.example.newsfeed.data.remote.models.redditModels.Entry
import com.example.newsfeed.data.remote.models.redditModels.NewsFeedReddit
import com.example.newsfeed.presentation.entityUi.NewsUi
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.Locale

fun NewsFeedReddit.mapToNewsUiReddit(): NewsUi {
    val defaultImage = icon

    return NewsUi(
        defaultImage, entries.map {
            it.mapToUi(defaultImage)
        })
}

private fun Entry.parseDescription(): String {
    content.let {
        val doc: Document = Jsoup.parse(content.value)
        val content = doc.select("content")
        val textContent = StringBuilder()
        for (p in content) {
            textContent.append(p.text()).append("\n")
        }
        return textContent.toString()
    }
}

private fun Entry.mapToUi(defaultImageUrl: String) = ItemNewsUi(
    id = id,
    image = defaultImageUrl,
    title = title,
    publishedAt = published,
    description = parseDescription(),
    addedBy = authorBy.name,
    isBookmarked = false,
    source = getNewsSource(link.href).sourceName,
    url = link.href,
    timeStamp = 0L
)

fun ItemNewsUi.mapToDBReddit(isBookmarked: Boolean): NewsDB {
    return NewsDB(
        id = id,
        image = image,
        title = title,
        publishedAt = publishedAt,
        description = description,
        addedBy = addedBy,
        isBookmarked = isBookmarked,
        source = source,
        url = url,
        timeStamp = 0L
    )
}

fun NewsDB.mapFromDBToUiReddit(): ItemNewsUi {
    return ItemNewsUi(
        id = id,
        image = image,
        title = title,
        publishedAt = publishedAt,
        description = description,
        addedBy = addedBy,
        isBookmarked = isBookmarked,
        source = source,
        url = url,
        timeStamp = 0L
    )
}

fun String.toNewsSource(): NewsSource {
    return when (this) {
        NewsSource.REDDIT.sourceName -> NewsSource.REDDIT
        NewsSource.HABR.sourceName -> NewsSource.HABR
        else -> NewsSource.UNKNOWN
    }
}

fun getNewsSource(link: String): NewsSource {
    return when {
        link.contains("reddit.com") -> NewsSource.REDDIT
        link.contains("habr.com") -> NewsSource.HABR
        else -> NewsSource.UNKNOWN
    }
}

private fun Item.parseTitle(): String {
    title.let {
        val doc: Document = Jsoup.parse(title)
        return doc.text()
    }
}

private fun Item.parseDescription(): String {
    description.let {
        val doc: Document = Jsoup.parse(description)
        val paragraphs = doc.select("p")
        val textContent = StringBuilder()
        for (p in paragraphs) {
            textContent.append(p.text()).append("\n")
        }
        return textContent.toString()
    }
}

private fun Item.parseImage(defaultImageUrl: String): String {

    val doc: Document = Jsoup.parse(description)
    val imageElements = doc.select("img")
    return if (imageElements.isNotEmpty()) {
        imageElements[0].attr("src")
    } else {
        defaultImageUrl
    }
}

private fun Item.mapToUi(defaultImageUrl: String): ItemNewsUi {
    val timestamp =
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(pubDate)?.time
            ?: System.currentTimeMillis()

    return ItemNewsUi(
        id = timestamp,
        image = parseImage(defaultImageUrl),
        title = parseTitle(),
        publishedAt = pubDate,
        description = parseDescription(),
        addedBy = authorArticle,
        isBookmarked = false,
        source = getNewsSource(link).sourceName,
        url = link,
        timeStamp = 0L
    )
}

fun NewsFeed.mapToNewsUi(): NewsUi {
    val defaultImage = channel.mainImage.url

    return NewsUi(
        defaultImage, channel.items.map {
            it.mapToUi(defaultImage)
        })
}

fun ItemNewsUi.mapToDB(isBookmarked: Boolean, ): NewsDB {
    return NewsDB(
        id = id,
        image = image,
        title = title,
        publishedAt = publishedAt,
        description = description,
        addedBy = addedBy,
        isBookmarked = isBookmarked,
        source = source,
        url = url,
        timeStamp = if (isBookmarked) {
            if (timeStamp == 0L) System.currentTimeMillis() else timeStamp
        } else 0L
    )
}

fun NewsDB.mapFromDBToUi(): ItemNewsUi {
    return ItemNewsUi(
        id = id,
        image = image,
        title = title,
        publishedAt = publishedAt,
        description = description,
        addedBy = addedBy,
        isBookmarked = isBookmarked,
        source = source,
        url = url,
        timeStamp = timeStamp
    )
}