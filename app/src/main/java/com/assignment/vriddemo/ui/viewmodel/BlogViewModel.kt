package com.assignment.vriddemo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.domain.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val repository: BlogRepository
) : ViewModel() {

    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts = _blogPosts.asStateFlow()

    init {
        viewModelScope.launch {
            if (_blogPosts.value.isEmpty()) {
                val posts = repository.getBlogPosts()
                _blogPosts.value = posts
            }
        }
    }
}
