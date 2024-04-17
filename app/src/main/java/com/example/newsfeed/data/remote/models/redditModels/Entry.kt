package com.example.newsfeed.data.remote.models.redditModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
data class Entry(

    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String? = null,

    @field:Element(name = "published")
    @param:Element(name = "published")
    val published: String = "",

    @field:Element(name = "content", required = false)
    @param:Element(name = "content", required = false)
    val content: Content? = null,

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: Link? = null,

    @field:Element(name = "author", required = false)
    @param:Element(name = "author", required = false)
    val authorBy: Author? = null
)