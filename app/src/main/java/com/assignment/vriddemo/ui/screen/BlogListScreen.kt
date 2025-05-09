package com.assignment.vriddemo.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.ui.components.NetworkErrorDialog

@Composable
fun BlogListScreen(
    posts: List<BlogPost>,
    isLoading: Boolean,
    onLoadMore: () -> Unit,
    onItemClick: (BlogPost) -> Unit,
    networkError: Boolean,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (networkError) {
        NetworkErrorDialog(onRetry = onRetry)
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        itemsIndexed(posts) { index, post ->
            BlogPostItem(post = post) {
                onItemClick(post)
            }

            if (index >= posts.size - 3) {
                LaunchedEffect(key1 = true) {
                    onLoadMore()
                }
            }
        }

        if (isLoading) {
            item {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    CircularProgressIndicator(modifier = modifier)

                    Text("Loading Data...")
                }
            }
        }
    }
}

@Composable
fun BlogPostItem(post: BlogPost, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.excerpt, style = MaterialTheme.typography.bodyMedium)
        }
    }
}