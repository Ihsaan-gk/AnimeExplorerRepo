package com.muhammedihsaan.animeexplorer.di

import com.muhammedihsaan.animeexplorer.data.remote.JikanApiService
import com.muhammedihsaan.animeexplorer.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitClient.create()
    }

    @Provides
    @Singleton
    fun provideJikanApi(retrofit: Retrofit): JikanApiService {
        return retrofit.create(JikanApiService::class.java)
    }
}