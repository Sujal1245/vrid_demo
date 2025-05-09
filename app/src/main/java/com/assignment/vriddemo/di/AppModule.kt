package com.assignment.vriddemo.di

import android.app.Application
import android.content.Context
import com.assignment.vriddemo.data.remote.BlogApiService
import com.assignment.vriddemo.data.repository.BlogRepositoryImpl
import com.assignment.vriddemo.domain.repository.BlogRepository
import com.assignment.vriddemo.domain.usecase.GetBlogPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBlogApiService(): BlogApiService {
        return Retrofit.Builder()
            .baseUrl("https://blog.vrid.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BlogApiService::class.java)
    }

    @Provides
    fun provideBlogRepository(api: BlogApiService): BlogRepository {
        return BlogRepositoryImpl(api)
    }

    @Provides
    fun provideGetBlogPostsUseCase(repository: BlogRepository): GetBlogPostsUseCase {
        return GetBlogPostsUseCase(repository)
    }
}
