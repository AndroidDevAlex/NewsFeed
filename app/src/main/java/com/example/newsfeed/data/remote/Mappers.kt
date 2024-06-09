package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsDB
import com.example.newsfeed.data.remote.models.habrModels.Item
import com.example.newsfeed.data.remote.models.redditModels.Entry
import com.example.newsfeed.presentation.NewsUi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.text.SimpleDateFormat
import java.util.Locale

private const val defaultImageHabr = "https://habrastorage.org/webt/ym/el/wk/ymelwk3zy1gawz4nkejl_-ammtc.png"

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
    return ""
}

private fun Entry.mapToUi() = NewsUi(
    id = TODO(),
    image = "",
    title = title,
    publishedAt = published,
    description = parseDescription(),
    addedBy = authorBy.name,
    isBookmarked = false,
    source = redditSource(link.href),
    url = link.href
)

private fun redditSource(link: String): String {
    return when {
        link.contains("reddit.com") -> "reddit"
        else -> ""
    }
}

fun List<Entry>.mapToUi(): List<NewsUi> {
    val result = mutableListOf<NewsUi>()
    this.forEach {
        result.add(it.mapToUi())
    }
    return result
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

private fun Item.parseImage(): String {

    val doc: Document = Jsoup.parse(description)
    val imageElements = doc.select("img")
    return if (imageElements.isNotEmpty()) {
        imageElements[0].attr("src")
    } else {
        defaultImageHabr
    }
}

private fun Item.mapToUi(): NewsUi {
    val timestamp =
        SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(pubDate)?.time
            ?: System.currentTimeMillis()

    return NewsUi(
        id = timestamp,
        image = parseImage(),
        title = parseTitle(),
        publishedAt = pubDate,
        description = parseDescription(),
        addedBy = authorArticle,
        isBookmarked = false,
        source = habrSource(link),
        url = link
    )
}

private fun habrSource(link: String): String {
    return when {
        link.contains("habr.com") -> "habr"
        else -> ""
    }
}

fun List<Item>.mapToNewsUi(): List<NewsUi> {
    val result = mutableListOf<NewsUi>()
    this.forEach {
        result.add(it.mapToUi())
    }
    return result
}

fun NewsUi.mapToDB(): NewsDB {
    return NewsDB(
        id = id,
        image = image,
        title = title,
        publishedAt = publishedAt,
        description = description,
        addedBy = addedBy,
        isBookmarked = true,
        source = source,
        url = url
    )
}

fun NewsDB.mapFromDBToUi(): NewsUi {
    return NewsUi(
        id = id,
        image = image,
        title = title,
        publishedAt = publishedAt,
        description = description,
        addedBy = addedBy,
        isBookmarked = true,
        source = source,
        url = url
    )
}