package com.muhammedihsaan.animeexplorer.data.repository

import com.muhammedihsaan.animeexplorer.domain.model.Anime
import com.muhammedihsaan.animeexplorer.domain.model.AnimeDetail

interface AnimeRepository {

    suspend fun getTopAnime(
        page: Int
    ): Result<List<Anime>>

    suspend fun getAnimeDetail(
        animeId: Int
    ): Result<AnimeDetail>
}
