package com.example.newsfeed.data.remote.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
data class Entry(
    @field:Element(name = "id")
    val id: Int = 0,

    @field:Element(name = "title")
    val title: String? = null,

    @field:Element(name = "category")
    val category: Category? = null,

    @field:Element(name = "updated")
    val updated: String? = null,

    @field:Element(name = "published")
    val published: String? = null,

    @field:Element(name = "content")
    val description: Content? = null,

    @field:Element(name = "link")
    val link: Link? = null,

    @field:Element(name = "author")
    val authorBy: Author? = null
) {
    constructor() : this(0, null, null, null, null, null, null, null)
}