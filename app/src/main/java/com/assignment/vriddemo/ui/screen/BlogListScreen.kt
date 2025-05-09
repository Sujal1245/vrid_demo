package com.assignment.vriddemo.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.ui.navigation.Screen

@Composable
fun BlogListScreen(
    posts: List<BlogPost>,
    onLoadMore: () -> Unit,
    onItemClick: (BlogPost) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
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