package com.example.newsfeed.data.remote.models.redditModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "author", strict = false)
data class Author(

    @field:Element(name = "name")
    val name: String?,

    @field:Element(name = "uri")
    val uri: String?
){
    constructor(): this(null, null)
}