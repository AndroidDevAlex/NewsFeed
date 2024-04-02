package com.example.newsfeed.data.remote.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
data class Entry (
    @field:Element(name = "id")
    val id: Int = 0,

    @field:Element(name = "title")
    val title: String? = null,

    @field:Element(name = "published")
    val published: String? = null,

    @field:Element(name = "content")
    val description: String? = null,

    @field:Element(name = "link")
    val link: String? = null,

    @field:Element(name = "author")
    val authorBy: Author? = null
)