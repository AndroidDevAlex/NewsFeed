package com.example.newsfeed.data.remote.models.redditModels


import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "link", strict = false)
data class Link(

    @field:Attribute(name = "rel")
    val rel: String?,

    @field:Attribute(name = "href")
    val href: String?,

    @field:Attribute(name = "type")
    val type: String?
)