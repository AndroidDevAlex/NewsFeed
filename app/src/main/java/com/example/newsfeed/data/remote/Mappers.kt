package com.example.newsfeed.data.remote

import com.example.newsfeed.data.local.NewsDB
import com.example.newsfeed.data.remote.models.habrModels.Item
import com.example.newsfeed.data.remote.models.redditModels.Entry
import com.example.newsfeed.presentation.NewsUi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

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
    id = generateNewId(),
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

private var currentId = 0

private fun generateNewId(): Int {
    return ++currentId
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

/*private fun Item.parseImage(): String {

    description.let {
        val doc: Document = Jsoup.parse(description)
        val imageElements = doc.select("img")
        if (imageElements.isNotEmpty()) {
            return imageElements[0].attr("src")
        }
    }
    return ""
}*/

private const val rssXmlResponse = """
    <rss xmlns:dc="http://purl.org/dc/elements/1.1/" version="2.0">
        <channel>
            <title>
                <![CDATA[ Все публикации подряд на Хабре ]]>
            </title>
            <link>https://habr.com/ru/articles/</link>
            <description>
                <![CDATA[ Все публикации подряд на Хабре ]]>
            </description>
            <language>ru</language>
            <managingEditor>editor@habr.com</managingEditor>
            <generator>habr.com</generator>
            <pubDate>Mon, 03 Jun 2024 13:23:01 GMT</pubDate>
            <image>
                <link>https://habr.com/ru/</link>
                <url>
                    https://habrastorage.org/webt/ym/el/wk/ymelwk3zy1gawz4nkejl_-ammtc.png
                </url>
                <title>Хабр</title>
            </image>
        </channel>
    </rss>
"""

private fun Item.parseImage(): String {

    val doc: Document = Jsoup.parse(description)
    val imageElements = doc.select("img")
    return if (imageElements.isNotEmpty()) {
        imageElements[0].attr("src")
    } else {
       // val rssImage: Document = Jsoup.parse(image.url)
        val rssDoc: Document = Jsoup.parse(rssXmlResponse)
        val imageElementRss = rssDoc.select("url")
        //val imageElementRss = rssImage.select("url")
        imageElementRss[0].text()
    }
}

 private fun Item.mapToUi() = NewsUi(
    id = generateNewId(),
    image = parseImage(),
    title = parseTitle(),
    publishedAt = pubDate,
    description = parseDescription(),
    addedBy = authorArticle,
    isBookmarked = false,
    source = habrSource(link),
    url = link,
    // imageUrl = image.url
)

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
        url = url,
        //imageUrl = imageUrl
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
        url = url,
      //  imageUrl = imageUrl
    )
}