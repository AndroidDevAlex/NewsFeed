package com.example.newsfeed.util

import android.os.Build
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
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun NewsFeedReddit.mapToNewsUiReddit(): NewsUi {
    val defaultImage = icon.removeSuffix("/")

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

private fun Entry.mapToUi(defaultImageUrl: String): ItemNewsUi {
    val formattedDate = formatDate(published, ConstantsSourceNames.REDDIT)

    return ItemNewsUi(
        id = id,
        image = defaultImageUrl,
        title = title,
        publishedAt = formattedDate,
        description = parseDescription(),
        addedBy = authorBy.name,
        isBookmarked = false,
        source = getNewsSource(link.href),
        url = link.href,
        timeStamp = 0L
    )
}

private fun getNewsSource(link: String): String {
    return when {
        link.contains("reddit.com") -> ConstantsSourceNames.REDDIT
        link.contains("habr.com") -> ConstantsSourceNames.HABR
        else -> "unknown"
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

private fun formatDate(dateString: String, source: String): String {
    val redditFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH)
    } else {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z", Locale.ENGLISH)
    }

    val habrFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)

    val inputFormat: SimpleDateFormat = when (source) {
        ConstantsSourceNames.REDDIT -> redditFormat
        ConstantsSourceNames.HABR -> habrFormat
        else -> throw IllegalArgumentException("Unknown news source")
    }

    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val date: Date = inputFormat.parse(dateString)
        ?: throw IllegalArgumentException("Cannot parse date: $dateString")

    val outputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm", Locale.ENGLISH)
    outputFormat.timeZone = TimeZone.getTimeZone("UTC")

    return outputFormat.format(date)
}

private fun Item.mapToUi(defaultImageUrl: String): ItemNewsUi {
    val formattedDate = formatDate(pubDate, ConstantsSourceNames.HABR)

    val timestamp =
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(pubDate)?.time
            ?: System.currentTimeMillis()

    return ItemNewsUi(
        id = timestamp.toString(),
        image = parseImage(defaultImageUrl),
        title = parseTitle(),
        publishedAt = formattedDate,
        description = parseDescription(),
        addedBy = authorArticle,
        isBookmarked = false,
        source = getNewsSource(link),
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

fun ItemNewsUi.mapToDB(isBookmarked: Boolean): NewsDB {
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