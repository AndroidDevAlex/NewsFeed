package com.example.newsfeed.data.remote.models.redditModels

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "content", strict = false)
data class Content(

    @field:Attribute(name = "type")
    val type: String?,

    @field:Text(required = false)
    val value: String?
)

