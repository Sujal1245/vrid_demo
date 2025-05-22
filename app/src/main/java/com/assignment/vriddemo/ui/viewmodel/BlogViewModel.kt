package com.assignment.vriddemo.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.vriddemo.domain.model.BlogPost
import com.assignment.vriddemo.domain.usecase.GetBlogPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val getBlogPostsUseCase: GetBlogPostsUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _blogPosts = MutableStateFlow<List<BlogPost>>(emptyList())
    val blogPosts = _blogPosts
        .onStart { loadNextPage() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _networkError = MutableStateFlow(false)
    val networkError = _networkError.asStateFlow()

    private var currentPage = 1
    private var endReached = false

    fun loadNextPage() {
        if (_isLoading.value || endReached) return

        if (!isNetworkAvailable(context)) {
            _networkError.value = true
            return
        }

        _isLoading.value = true

        Log.i("Info", "Loading Data...")

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
            } catch (_: Exception) {
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

    fun checkNetworkStatus(): Boolean {
        val isAvailable = isNetworkAvailable(context)
        _networkError.value = !isAvailable
        return isAvailable
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

