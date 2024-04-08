package com.example.newsfeed.data.remote.models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "content", strict = false)
data class Content(
    @field:Attribute(name = "type")
    val type: String? = null,

    @field:Text
    val value: String? = null
)

