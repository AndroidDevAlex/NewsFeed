package com.example.newsfeed.data.remote.models.habrModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
data class ItemImage(

    @field:Element(name = "url")
    @param:Element(name = "url")
    val url: String = "",
)