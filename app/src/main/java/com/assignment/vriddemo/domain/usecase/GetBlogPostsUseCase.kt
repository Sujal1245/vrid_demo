package com.assignment.vriddemo.domain.usecase

import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.domain.repository.BlogRepository

class GetBlogPostsUseCase(private val blogRepository: BlogRepository) {
    suspend fun execute(page: Int): List<BlogPost> {
        return blogRepository.getBlogPosts(page)
    }
}