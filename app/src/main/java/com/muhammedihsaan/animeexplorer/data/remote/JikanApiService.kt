package com.muhammedihsaan.animeexplorer.data.remote

import com.muhammedihsaan.animeexplorer.data.model.AnimeDetailResponse
import com.muhammedihsaan.animeexplorer.data.model.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {

    @GET("v4/top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int
    ): TopAnimeResponse

    @GET("v4/anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") animeId: Int
    ): AnimeDetailResponse
}