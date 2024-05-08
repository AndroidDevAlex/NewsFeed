package com.example.newsfeed.data.remote.models.redditModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "feed", strict = false)
@Namespace(reference = "http://www.w3.org/2005/Atom")
data class NewsFeed(

   @field:Element(name = "icon")
   @param:Element(name = "icon")
   val icon: String = "",

   @field:ElementList(inline = true, entry = "entry")
   @param:ElementList(inline = true, entry = "entry")
   val entries: List<Entry> = emptyList()
)