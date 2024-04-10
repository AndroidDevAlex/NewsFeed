package com.example.newsfeed.data.remote.models.habrModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "guid", strict = false)
data class Guid(

    // @field:Element(name = "isPermaLink", required = false, type = Boolean::class)
    @field:Element(name = "isPermaLink", required = false)
    val isPermaLink: Boolean,

    @field:Text(required = false)
    val value: String?
){
    constructor() : this(false, null)
}