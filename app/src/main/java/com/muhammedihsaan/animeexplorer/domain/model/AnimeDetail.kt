package com.muhammedihsaan.animeexplorer.domain.model

data class AnimeDetail(
    val id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val rating: Double?,
    val genres: List<String>?,
    val videoUrl: String?,
    val trailerUrl: String?,
    val imageUrl: String
)