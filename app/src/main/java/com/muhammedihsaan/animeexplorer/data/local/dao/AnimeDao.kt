package com.muhammedihsaan.animeexplorer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammedihsaan.animeexplorer.data.local.entity.AnimeEntity

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime")
    suspend fun getAnime(): List<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(animeList: List<AnimeEntity>)

    @Query("DELETE FROM anime")
    suspend fun clearAnime()

    @Query("SELECT * FROM anime WHERE id = :animeId LIMIT 1")
    suspend fun getAnimeById(animeId: Int): AnimeEntity?
}