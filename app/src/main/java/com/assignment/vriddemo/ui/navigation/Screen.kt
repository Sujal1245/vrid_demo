package com.assignment.vriddemo.ui.navigation

sealed class Screen(val route: String) {
    object BlogList : Screen("blog_list")
    object BlogDetail : Screen("blog_detail/{blogId}") {
        fun passId(id: Int) = "blog_detail/$id"
    }
}