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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.ui.navigation.Screen
import com.assignment.vriddemo.ui.viewmodel.BlogViewModel

@Composable
fun BlogListScreen(
    navController: NavController,
    viewModel: BlogViewModel = hiltViewModel()
) {
    val blogPosts = viewModel.blogPosts.collectAsStateWithLifecycle().value
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        if (blogPosts.isEmpty()) {
            viewModel.getBlogPosts()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            BlogList(posts = blogPosts, navController = navController)
        }
    }
}

@Composable
fun BlogList(posts: List<BlogPost>, navController: NavController) {
    LazyColumn {
        itemsIndexed(posts) { index, post ->
            BlogPostItem(post) {
                navController.navigate(Screen.BlogDetail.passId(post.id))
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
            Text(text = post.excerpt, style = MaterialTheme.typography.bodySmall)
        }
    }
}