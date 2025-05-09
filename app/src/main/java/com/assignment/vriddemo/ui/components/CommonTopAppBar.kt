package com.assignment.vriddemo.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.assignment.vriddemo.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(navController: NavController) {
    // Get the current back stack entry to determine which screen we are on
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // Determine the title based on the current route
    val currentScreen = currentBackStackEntry?.destination?.route

    val title = when (currentScreen) {
        Screen.BlogList.route -> "Blog List"
        Screen.BlogDetail.route -> "Blog Detail"
        else -> "Vrid App"
    }

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            // Show back button only on the BlogDetail screen
            if (currentScreen == Screen.BlogDetail.route) {
                IconButton(onClick = {
                    // Navigate back to the previous screen
                    navController.popBackStack()
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}
