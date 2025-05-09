package com.assignment.vriddemo.ui.navigation

sealed class Screen(val route: String) {
    object BlogList : Screen("blogList")
    object BlogDetail : Screen("blogDetail/{blogId}") {
        fun passId(blogId: Int): String = "blogDetail/$blogId"
    }
}