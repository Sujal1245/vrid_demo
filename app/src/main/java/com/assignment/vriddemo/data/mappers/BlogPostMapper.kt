package com.assignment.vriddemo.data.mappers

import com.assignment.vriddemo.data.remote.BlogPostDto
import com.assignment.vriddemo.domain.model.BlogPost
import android.text.Html
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale

fun BlogPostDto.toDomainModel(): BlogPost {
    val imageUrl = this.jetpackFeaturedMediaUrl.orEmpty()
    val readTime = extractReadTime(this.content.rendered)

    Log.d("BlogPostMapper", "Image URL: $imageUrl, Read Time: $readTime")

    val publishDate = parsePublishDate(this.date)

    return BlogPost(
        id = this.id,
        title = decodeHtmlEntities(this.title.rendered).trim(),
        excerpt = decodeHtmlEntities(this.excerpt.rendered).trim(),
        imageUrl = imageUrl,
        publishDate = publishDate,
        readTime = readTime,
        url = this.link
    )
}

fun decodeHtmlEntities(input: String): String {
    return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).toString()
}


fun parsePublishDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    return try {
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: ""
    } catch (_: Exception) {
        ""
    }
}

fun extractReadTime(content: String): String {
    val startKeyword = "Estimated read time: "
    val startIndex = content.indexOf(startKeyword)

    return if (startIndex != -1) {
        val subString = content.substring(startIndex + startKeyword.length)
        val endIndex = subString.indexOf("</mark>")
        if (endIndex != -1) {
            subString.substring(0, endIndex).trim()
        } else {
            subString.trim()
        }
    } else {
        "Not available"
    }
}