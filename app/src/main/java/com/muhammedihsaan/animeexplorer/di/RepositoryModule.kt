package com.muhammedihsaan.animeexplorer.di

import com.muhammedihsaan.animeexplorer.data.repository.AnimeRepository
import com.muhammedihsaan.animeexplorer.data.repository.AnimeRepositoryImpl
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
    abstract fun bindAnimeRepository(
        impl: AnimeRepositoryImpl
    ): AnimeRepository
}