package com.example.newsfeed.data.remote.models.redditModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "entry", strict = false)
data class Entry(

    @field:Element(name = "id")
    val id: String?,

    @field:Element(name = "title")
    val title: String?,

    @field:Element(name = "category")
    val category: Category?,

    @field:Element(name = "updated")
    val updated: String?,

    @field:Element(name = "published")
    val published: String?,

    @field:Element(name = "content")
    val description: Content?,

    @field:Element(name = "link")
    val link: Link?,

    @field:Element(name = "author")
    val authorBy: Author?
)