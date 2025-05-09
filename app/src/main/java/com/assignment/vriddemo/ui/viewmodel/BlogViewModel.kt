package com.assignment.vriddemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.domain.usecase.GetBlogPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val getBlogPostsUseCase: GetBlogPostsUseCase
) : ViewModel() {

    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts = _blogPosts.asStateFlow()

    private var currentPage = 1
    private var isLoading = false
    private var endReached = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading || endReached) return

        isLoading = true
        viewModelScope.launch {
            try {
                val newPosts = getBlogPostsUseCase.execute(currentPage)
                if (newPosts.isEmpty()) {
                    endReached = true
                } else {
                    _blogPosts.value = _blogPosts.value + newPosts
                    currentPage++
                }
            } catch (e: Exception) {
                //Handle any errors
            } finally {
                isLoading = false
            }
        }
    }
}
