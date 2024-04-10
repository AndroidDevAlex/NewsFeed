package com.example.newsfeed.data.remote.models.habrModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
data class Image(
    @field:Element(name = "link")
    @param:Element(name = "link", required = false)
    val link: String?,

    @field:Element(name = "url")
    @param:Element(name = "url", required = false)
    val url: String?,

    @field:Element(name = "title")
    @param:Element(name = "title", required = false)
    val title: String?
){

    constructor(): this(null, null, null)
}