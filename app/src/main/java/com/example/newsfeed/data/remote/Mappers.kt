package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsDB
import com.example.newsfeed.data.remote.models.habrModels.Item
import com.example.newsfeed.data.remote.models.redditModels.Entry
import com.example.newsfeed.presentation.NewsUi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

private fun Entry.parseDescription(): String{
    content?.let {
        val doc: Document = Jsoup.parse(content.value?: "")
        val content = doc.select("content")
        val textContent = StringBuilder()
        for(p in content){
            textContent.append(p.text()).append("\n")
        }
        return textContent.toString()
    }
    return ""
}

private fun Entry.mapToUi() = NewsUi(
    id = generateNewId(),
    image = "",
    title = title ?: "",
    publishedAt = published,
    description = parseDescription(),
    addedBy = authorBy?.name ?: "",
    isBookmarked = false,
    source = link?.href ?: ""
)

fun List<Entry>.mapToUi(): List<NewsUi> {
    val result = mutableListOf<NewsUi>()
    this.forEach {
        result.add(it.mapToUi())
    }
    return result
}

private var currentId = 0

private fun generateNewId(): Int {
    return ++currentId
}

private fun Item.parseTitle(): String {
    title?.let {
        val doc: Document = Jsoup.parse(title)
        return doc.text()
    }
    return ""
}

private fun Item.parseDescription(): String {
    description?.let {
        val doc: Document = Jsoup.parse(description)
        val paragraphs = doc.select("p")
        val textContent = StringBuilder()
        for (p in paragraphs) {
            textContent.append(p.text()).append("\n")
        }
        return textContent.toString()
    }
    return ""
}

private fun Item.parseImage(): String {

    description?.let {
        val doc: Document = Jsoup.parse(description)
        val imageElements = doc.select("img")
        if (imageElements.isNotEmpty()) {
            return imageElements[0].attr("src")
        }
    }
    return ""// вернуть заглушку
}

private fun Item.mapToUi() = NewsUi(
    id = generateNewId(),
    image = parseImage(),
    title = parseTitle(),
    publishedAt = pubDate,
    description = parseDescription(),
    addedBy = authorArticle ?: "",
    isBookmarked = false,
    source = link ?: ""
)

fun List<Item>.mapToNewsUi(): List<NewsUi> {
    val result = mutableListOf<NewsUi>()
    this.forEach {
        result.add(it.mapToUi())
    }
    return result
}

fun Item.mapToUiId() = NewsUi(
    id = generateNewId(),
    image = parseImage(),
    title = parseTitle(),
    publishedAt = pubDate,
    description = parseDescription(),
    addedBy = authorArticle ?: "",
    isBookmarked = false,
    source = link ?: ""
)

fun NewsUi.mapToDB(): NewsDB {
    return NewsDB(
        id = id,
        image = image ?: "",
        title = title,
        publishedAt = publishedAt,
        description = description,
        addedBy = addedBy,
        isBookmarked = true,
        source = source
    )
}

fun List<NewsDB>.mapFromDBToUi(): List<NewsUi> {
    val result = mutableListOf<NewsUi>()
    this.forEach{
        result.add(it.mapToUi())
    }
    return result
}

private fun NewsDB.mapToUi() = NewsUi(
    id = generateNewId(),
    image = image,
    title = title,
    publishedAt = publishedAt,
    description = description,
    addedBy = addedBy,
    isBookmarked = true,
    source = source
)