package com.assignment.vriddemo.data.repository

import com.assignment.vriddemo.data.mappers.toDomainModel
import com.assignment.vriddemo.data.remote.BlogApiService
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.domain.repository.BlogRepository

class BlogRepositoryImpl(
    private val apiService: BlogApiService
) : BlogRepository {

    override suspend fun getBlogPosts(page: Int): List<BlogPost> {
        // Fetch data from API (BlogPostDto)
        val blogPostsDto = apiService.getBlogPosts(perPage = 10, page = page)

        // Map the DTOs to domain models (BlogPost)
        return blogPostsDto.map { it.toDomainModel() }
    }
}