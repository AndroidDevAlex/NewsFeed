package com.example.newsfeed.data.remote.models.habrModels


import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Item(

    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String = "",

    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String = "",

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false)
    val description: String = "",

    @field:Element(name = "pubDate")
    @param:Element(name = "pubDate")
    val pubDate: String = "",

    @field:Element(name = "creator", required = false)
    @param:Element(name = "creator", required = false)
    val authorArticle: String = "",

    @field:ElementList(inline = true, entry = "category")
    @param:ElementList(inline = true, entry = "category")
    val categories: List<String> = emptyList(),

   /* @field:Element(name = "image")
    @param:Element(name = "image")
    val image: ItemImage*/
)