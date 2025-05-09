package com.assignment.vriddemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
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
                BlogNavGraph(navController = navController)
            }
        }
    }
}