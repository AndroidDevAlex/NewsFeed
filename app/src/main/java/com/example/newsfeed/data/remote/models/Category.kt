package com.example.newsfeed.data.remote.models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "category", strict = false)
data class Category(
    @field:Attribute(name = "term")
    val term: String? = null,

    @field:Attribute(name = "label")
    val label: String? = null
)