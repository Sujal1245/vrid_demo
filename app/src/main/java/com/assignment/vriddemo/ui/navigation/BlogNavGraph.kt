package com.assignment.vriddemo.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.assignment.vriddemo.ui.screen.BlogDetailScreen
import com.assignment.vriddemo.ui.screen.BlogListScreen
import com.assignment.vriddemo.ui.viewmodel.BlogViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun BlogNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.BlogList.route
    ) {
        // Blog List Screen
        composable(Screen.BlogList.route) { backStackEntry ->
            val viewModel: BlogViewModel = hiltViewModel()
            val posts by viewModel.blogPosts.collectAsStateWithLifecycle()

            BlogListScreen(
                posts = posts,
                navController = navController,
                modifier = modifier
            )
        }

        // Blog Detail Screen
        composable(
            route = Screen.BlogDetail.route,
            arguments = listOf(navArgument("blogId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Remember the backstack entry from the BlogList screen
            val parentEntry = remember(navController) {
                navController.getBackStackEntry(Screen.BlogList.route)
            }

            // Use the parent entry to get the ViewModel shared across screens
            val viewModel: BlogViewModel = hiltViewModel(parentEntry)
            val blogId = backStackEntry.arguments?.getInt("blogId") ?: -1

            BlogDetailScreen(
                blogId = blogId,
                viewModel = viewModel,
                modifier = modifier
            )
        }
    }
}

