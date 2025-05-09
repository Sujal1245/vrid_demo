package com.assignment.vriddemo.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.domain.usecase.GetBlogPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val getBlogPostsUseCase: GetBlogPostsUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts = _blogPosts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _networkError = MutableStateFlow(false)
    val networkError = _networkError.asStateFlow()

    private var currentPage = 1
    private var endReached = false

    init {
        if (_blogPosts.value.isEmpty()) {
            loadNextPage()
        }
    }

    fun loadNextPage() {
        if (_isLoading.value || endReached) return

        if (!isNetworkAvailable(context)) {
            _networkError.value = true
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newPosts = getBlogPostsUseCase.execute(currentPage)
                if (newPosts.isEmpty()) {
                    endReached = true
                } else {
                    _blogPosts.value = _blogPosts.value + newPosts
                    currentPage++
                }
                _networkError.value = false  // Reset network error if data is fetched successfully
            } catch (e: Exception) {
                _networkError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun retry() {
        // Reset necessary state before retrying
        endReached = false
        currentPage = 1
        _networkError.value = false
        loadNextPage()  // Retry loading posts
    }

    fun updateNetworkStatus() {
        _networkError.value = !isNetworkAvailable(context)
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_CELLULAR
    )
}

