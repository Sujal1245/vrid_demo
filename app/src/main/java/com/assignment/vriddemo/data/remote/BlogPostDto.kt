package com.assignment.vriddemo.data.remote

data class BlogPostDto(
    val id: Int,
    val title: Rendered,
    val excerpt: Rendered,
    val link: String
)

data class Rendered(
    val rendered: String
)