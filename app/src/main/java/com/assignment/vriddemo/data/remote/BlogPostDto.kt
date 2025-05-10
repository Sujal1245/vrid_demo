package com.assignment.vriddemo.data.remote

import com.google.gson.annotations.SerializedName

data class BlogPostDto(
    val id: Int,
    val title: Rendered,
    val excerpt: Rendered,
    val content: Rendered,
    val link: String,
    val date: String,

    @SerializedName("jetpack_featured_media_url")
    val jetpackFeaturedMediaUrl: String?,
)

data class Rendered(
    val rendered: String
)