package com.example.newsfeed.data.remote.models.redditModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "author", strict = false)
data class Author(

    @field:Element(name = "name")
    @param:Element(name = "name")
    val name: String = "",
)