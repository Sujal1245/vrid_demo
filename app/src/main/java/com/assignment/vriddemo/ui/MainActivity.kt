package com.assignment.vriddemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.assignment.vriddemo.ui.components.CommonTopAppBar
import com.assignment.vriddemo.ui.navigation.BlogNavGraph
import com.assignment.vriddemo.ui.theme.VridDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VridDemoTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        CommonTopAppBar(navController) // Use the TopAppBar here
                    },
                    content = { innerPadding ->
                        BlogNavGraph(navController = navController, modifier = Modifier.padding(innerPadding)) // Your navigation graph
                    }
                )
            }
        }
    }
}