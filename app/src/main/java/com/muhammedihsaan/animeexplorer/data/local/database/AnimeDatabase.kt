package com.muhammedihsaan.animeexplorer.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammedihsaan.animeexplorer.data.local.dao.AnimeDao
import com.muhammedihsaan.animeexplorer.data.local.entity.AnimeEntity

@Database(
    entities = [AnimeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao
}