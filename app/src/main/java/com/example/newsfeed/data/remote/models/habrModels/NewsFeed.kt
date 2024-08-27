package com.example.newsfeed.data.remote.models.habrModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class NewsFeed(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: Channel
)