package com.assignment.vriddemo.domain.model

data class BlogPost(
    val id: Int,
    val title: String,
    val excerpt: String,
    val url: String,
    val imageUrl: String,
    val publishDate: String,
    val readTime: String
)