package com.example.newsfeed.data.remote.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "feed", strict = false)
@Namespace(reference = "http://www.w3.org/2005/Atom")
 data class ResponseNews (

    @field:Element(name = "id")
    val id: Int? = null,

    @field:Element(name = "title")
    val title: String? = null,

    @field:Element(name = "icon")
    val image: String? = null,

    @field:Element(name = "subtitle")
    val subtitle: String? = null,

    @field:Element(name = "category")
    val category: Category? = null,

    @field:Element(name = "updated")
    val updated: String? = null,

    @field:Element(name = "link")
    val link: Link? = null,

    @field:Element(name = "logo")
    val logo: String? = null,

    @field:ElementList(inline = true, entry = "entry", required = false)
    val entries: List<Entry>? = null
)