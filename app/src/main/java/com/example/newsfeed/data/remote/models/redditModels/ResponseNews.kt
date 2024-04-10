package com.example.newsfeed.data.remote.models.redditModels

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "feed", strict = false)
@Namespace(reference = "http://www.w3.org/2005/Atom")
data class ResponseNews(

   @field:Element(name = "id")
   val id: String?,

   @field:Element(name = "title")
   val title: String?,

   @field:Element(name = "icon")
   val image: String?,

   @field:Element(name = "subtitle")
   val subtitle: String?,

   @field:Element(name = "category")
   val category: Category?,

   @field:Element(name = "updated")
   val updated: String?,

   @field:Element(name = "link")
   val link: Link?,

   @field:Element(name = "logo")
   val logo: String?,

   @field:ElementList(inline = true, entry = "entry", required = false)
   val entries: List<Entry>?
)