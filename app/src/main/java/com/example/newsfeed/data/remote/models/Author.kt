package com.example.newsfeed.data.remote.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "author", strict = false)
data class Author(

    @field:Element(name = "name")
    val name: String? = null,

    @field:Element(name = "uri")
    val uri: String? = null

)