package com.assignment.vriddemo.ui.screen

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.assignment.vriddemo.domain.model.BlogPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailScreen(
    blogId: Int,
    onLoadBlogDetail: (Int) -> BlogPost?, // Callback to load blog detail
    modifier: Modifier = Modifier
) {
    // Get the specific blog post using the callback
    val blogPost = onLoadBlogDetail(blogId)

    if (blogPost == null) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        BlogWebView(url = blogPost.url, modifier = modifier)
    }
}



@Composable
fun BlogWebView(url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var rawProgress by remember { mutableIntStateOf(0) }

    // Animate progress value smoothly
    val animatedProgress by animateFloatAsState(
        targetValue = rawProgress / 100f,
        animationSpec = tween(durationMillis = 300),
        label = "WebViewProgress"
    )

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
        }
    }

    DisposableEffect(Unit) {
        webView.webViewClient = WebViewClient()

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                rawProgress = newProgress
            }
        }

        webView.loadUrl(url)

        onDispose {
            webView.stopLoading()
            webView.destroy()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        if (animatedProgress in 0f..0.99f) {
            LinearProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        AndroidView(
            factory = { webView },
            modifier = Modifier.fillMaxSize()
        )
    }
}



