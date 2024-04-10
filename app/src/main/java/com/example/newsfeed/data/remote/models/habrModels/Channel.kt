package com.example.newsfeed.data.remote.models.habrModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel(
    @field:Element(name = "title")
    val title: String?,

    @field:Element(name = "link")
    val link: String?,

    @field:Element(name = "description")
    val description: String?,

    @field:Element(name = "language")
    val language: String,

    @field:Element(name = "managingEditor")
    val managingEditor: String,

    @field:Element(name = "generator")
    val generator: String,

    @field:Element(name = "pubDate")
    val pubDate: String,

    @field:Element(name = "image")
    val image: Image?,

    @field:ElementList(inline = true, entry = "item")
    val items: List<Item>?
)