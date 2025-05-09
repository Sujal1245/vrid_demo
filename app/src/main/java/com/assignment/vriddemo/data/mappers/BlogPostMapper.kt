package com.assignment.vriddemo.data.mappers

import com.assignment.vriddemo.data.remote.BlogPostDto
import com.assignment.vriddemo.domain.model.BlogPost
import android.text.Html

fun BlogPostDto.toDomainModel(): BlogPost {
    return BlogPost(
        id = this.id,
        title = decodeHtmlEntities(this.title.rendered).trim(),
        excerpt = decodeHtmlEntities(this.excerpt.rendered).trim(),
        url = this.link
    )
}

fun decodeHtmlEntities(input: String): String {
    return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).toString()
}