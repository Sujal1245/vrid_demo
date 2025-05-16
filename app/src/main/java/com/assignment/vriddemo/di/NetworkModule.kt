package com.assignment.vriddemo.di

import com.assignment.vriddemo.data.remote.BlogApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBlogApiService(): BlogApiService {
        return Retrofit.Builder()
            .baseUrl("https://blog.vrid.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BlogApiService::class.java)
    }
}