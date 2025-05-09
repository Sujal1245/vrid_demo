package com.assignment.vriddemo.ui.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.assignment.vriddemo.ui.viewmodel.BlogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailScreen(
    blogId: Int,
    viewModel: BlogViewModel,
    modifier: Modifier = Modifier
) {
    val blogPosts by viewModel.blogPosts.collectAsStateWithLifecycle()

    if (blogPosts.isEmpty()) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        val blogPost = blogPosts.find { it.id == blogId }
        if (blogPost == null) {
            Text("Blog not found")
        } else {
            BlogWebView(url = blogPost.url, modifier = modifier)
        }
    }
}


@Composable
fun BlogWebView(url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
            }
        },
        modifier = modifier.fillMaxSize()
    )
}
