package com.example.omniaxion.di

import com.example.omniaxion.data.repository.AuthRepositoryImpl
import com.example.omniaxion.data.repository.NewsRepositoryImpl
import com.example.omniaxion.domain.repository.AuthRepository
import com.example.omniaxion.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
