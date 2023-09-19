package com.helpet.module

import com.example.domain.repository.BookRepository
import com.example.domain.usecase.BookUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideBookUseCase(repository: BookRepository): BookUseCase {
        return BookUseCase(repository)
    }
}