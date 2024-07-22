package com.example.newsfeed.data.remote.models.redditModels

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "link", strict = false)
data class Link(

    @field:Attribute(name = "href", required = true)
    @param:Attribute(name = "href", required = true)
    val href: String = ""
)