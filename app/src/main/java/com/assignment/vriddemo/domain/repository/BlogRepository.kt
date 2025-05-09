package com.assignment.vriddemo.domain.repository

import com.assignment.vriddemo.domain.model.BlogPost

interface BlogRepository {
    suspend fun getBlogPosts(page: Int): List<BlogPost>
}