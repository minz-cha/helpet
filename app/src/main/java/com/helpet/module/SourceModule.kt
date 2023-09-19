package com.helpet.module

import com.example.data.source.local.bookLocalSource
import com.example.data.source.local.bookLocalSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SourceModule {
    @Provides
    fun provideUserSource(bookLocalSourceImpl: bookLocalSourceImpl): bookLocalSource = bookLocalSourceImpl
}
