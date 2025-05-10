package com.assignment.vriddemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.assignment.vriddemo.R
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
            BlogPostItem(post = post, onClick = { onItemClick(post) })

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogPostItem(post: BlogPost, onClick: (BlogPost) -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = { onClick(post) },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = post.imageUrl,
                contentDescription = post.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = post.excerpt,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = post.publishDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        painter = painterResource(R.drawable.sand_clock),
                        contentDescription = "Sand Clock Icon"
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = post.readTime,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}