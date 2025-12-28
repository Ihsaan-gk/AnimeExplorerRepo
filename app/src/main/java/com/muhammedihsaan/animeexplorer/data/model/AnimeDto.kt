package com.muhammedihsaan.animeexplorer.data.model

data class AnimeDto(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val images: AnimeImageDto
)

data class AnimeImageDto(
    val jpg: JpgImageDto
)

data class JpgImageDto(
    val image_url: String,
    val large_image_url: String
)