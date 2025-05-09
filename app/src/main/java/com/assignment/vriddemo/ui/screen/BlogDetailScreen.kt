package com.assignment.vriddemo.ui.screen

import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.ui.components.NetworkErrorDialog
import com.assignment.vriddemo.ui.theme.VridDemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailScreen(
    blogId: Int,
    onLoadBlogDetail: (Int) -> BlogPost?, // Callback to load blog detail
    networkError: Boolean,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Get the specific blog post using the callback
    val blogPost = onLoadBlogDetail(blogId)

    if (blogPost == null) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        BlogWebView(
            url = blogPost.url,
            networkError = networkError,
            onRetry = onRetry,
            modifier = modifier
        )
    }
}

@Composable
fun BlogWebView(
    url: String,
    networkError: Boolean,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var rawProgress by remember { mutableIntStateOf(0) }
    var showNetworkErrorDialog by remember { mutableStateOf(false) }

    val savedUrl = rememberSaveable { mutableStateOf(url) }

    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
        }
    }

    // To track loading progress of the WebView
    val animatedProgress by animateFloatAsState(
        targetValue = rawProgress / 100f,
        animationSpec = tween(durationMillis = 300),
        label = "WebViewProgress"
    )

    // If network error occurs, show the error dialog
    if (networkError || showNetworkErrorDialog) {
        NetworkErrorDialog(onRetry = {
            onRetry()
            showNetworkErrorDialog = false
        })
        return // Don't show WebView when there's a network error
    }

    // Column to manage WebView and progress
    Column(modifier = modifier.fillMaxSize()) {
        if (animatedProgress in 0f..0.99f) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier.fillMaxWidth(),
                color = Color.Gray,
                trackColor = Color.White
            )
        }

        // Key based on savedUrl to prevent unnecessary reloads
        key(savedUrl.value) {
            AndroidView(
                factory = {
                    webView.apply {
                        // Set WebViewClient to handle errors
                        webViewClient = object : WebViewClient() {
                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                if (request?.isForMainFrame == true) {
                                    showNetworkErrorDialog = true
                                }
                            }
                        }

                        // Set WebChromeClient to track the loading progress
                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                rawProgress = newProgress
                            }
                        }

                        // Only load the URL if it's not already loaded
                        if (url != webView.url) {
                            loadUrl(url)
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
