package com.muhammedihsaan.animeexplorer.data.repository

import com.muhammedihsaan.animeexplorer.data.local.dao.AnimeDao
import com.muhammedihsaan.animeexplorer.data.mapper.toDomain
import com.muhammedihsaan.animeexplorer.data.mapper.toDomainDetail
import com.muhammedihsaan.animeexplorer.data.mapper.toEntity
import com.muhammedihsaan.animeexplorer.data.remote.JikanApiService
import com.muhammedihsaan.animeexplorer.domain.model.Anime
import com.muhammedihsaan.animeexplorer.domain.model.AnimeDetail
import com.muhammedihsaan.animeexplorer.utils.NetworkHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val api: JikanApiService,
    private val dao: AnimeDao,
    private val networkHelper: NetworkHelper
) : AnimeRepository {

    override suspend fun getTopAnime(page: Int): Result<List<Anime>> {
        return try {
            if (networkHelper.isNetworkConnected()) {

                val response = api.getTopAnime(page)
                val animeList = response.data.map { it.toDomain() }

                if (page == 1) { dao.clearAnime() }
                dao.insertAnime(animeList.map { it.toEntity() }) // Cache to DB
                Result.success(animeList)
            } else {
                val cachedAnime = dao.getAnime() // Offline load from DB
                if (cachedAnime.isNotEmpty()) {
                    Result.success(cachedAnime.map { it.toDomain() })
                } else {
                    Result.failure(Exception("No internet connection"))
                }
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAnimeDetail(animeId: Int): Result<AnimeDetail> {
        return try {
            if (networkHelper.isNetworkConnected()) {
                val response = api.getAnimeDetails(animeId)
                Result.success(response.data.toDomain())
            } else {
                val cached = dao.getAnimeById(animeId)
                cached?.let {
                    Result.success(it.toDomainDetail())
                } ?: Result.failure(Exception("No offline data"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}