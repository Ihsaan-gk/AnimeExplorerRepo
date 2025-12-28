package com.muhammedihsaan.animeexplorer.data.model

data class AnimeDetailDto(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?,
    val images: AnimeImageDto,
    val trailer: AnimeTrailerDto?,
    val genres: List<GenreDto>
)

data class AnimeTrailerDto(
    val youtube_id: String?,
    val url: String?,
    val embed_url : String?
)

data class GenreDto(
    val name: String
)