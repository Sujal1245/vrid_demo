package com.assignment.vriddemo.di

import com.assignment.vriddemo.data.repository.BlogRepositoryImpl
import com.assignment.vriddemo.domain.repository.BlogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindBlogRepository(
        blogRepositoryImpl: BlogRepositoryImpl
    ): BlogRepository
}