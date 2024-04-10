package com.example.newsfeed.data.remote.models.habrModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "item", strict = false)
data class Item(

    @field:Element(name = "title")
    val title: String?,

    @field:Element(name = "guid")
    val guid: Guid,

    @field:Element(name = "link")
    val link: String?,

    @field:Element(name = "description")
    val description: String?,

    @field:Element(name = "pubDate")
    val pubDate: String,

    @field:Element(name = "creator", required = false)
    @field:Text(required = false)
    val authorArticle: String?,

    @field:ElementList(inline = true, entry = "category")
    val categories: List<String>?
)