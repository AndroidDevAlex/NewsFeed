package com.example.newsfeed.data.remote.models.habrModels

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class Channel(

    @field:ElementList(inline = true, entry = "item")
    @param:ElementList(inline = true, entry = "item")
    val items: List<Item> = emptyList()
)