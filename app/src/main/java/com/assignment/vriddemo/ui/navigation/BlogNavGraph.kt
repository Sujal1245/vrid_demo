package com.assignment.vriddemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.assignment.vriddemo.ui.screen.BlogDetailScreen
import com.assignment.vriddemo.ui.screen.BlogListScreen

@Composable
fun BlogNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.BlogList.route) {
        composable(Screen.BlogList.route) {
            BlogListScreen(navController = navController)
        }
        composable(route = Screen.BlogDetail.route) { backStackEntry ->
            val blogId = backStackEntry.arguments?.getString("blogId")?.toIntOrNull()
            blogId?.let {
                BlogDetailScreen(navController, blogId = it)
            }
        }
    }
}