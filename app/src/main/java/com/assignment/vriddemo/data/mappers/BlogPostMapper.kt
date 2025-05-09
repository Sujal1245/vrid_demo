package com.assignment.vriddemo.data.mappers

import com.assignment.vriddemo.data.remote.BlogPostDto
import com.assignment.vriddemo.domain.model.BlogPost

fun BlogPostDto.toDomainModel(): BlogPost {
    return BlogPost(
        id = this.id,
        title = this.title.rendered,
        excerpt = this.excerpt.rendered,
        url = this.link
    )
}