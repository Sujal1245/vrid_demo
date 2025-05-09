package com.assignment.vriddemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.domain.usecase.GetBlogPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val getBlogPostsUseCase: GetBlogPostsUseCase
) : ViewModel() {

    // State to hold the list of blog posts
    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts: StateFlow<List<BlogPost>> get() = _blogPosts

    // State to handle loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Fetch blog posts
    fun getBlogPosts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val posts = getBlogPostsUseCase.execute()
                _blogPosts.value = posts
            } catch (e: Exception) {
                _blogPosts.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}