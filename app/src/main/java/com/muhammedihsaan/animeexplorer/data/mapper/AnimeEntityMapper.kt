package com.muhammedihsaan.animeexplorer.data.mapper

import com.muhammedihsaan.animeexplorer.data.local.entity.AnimeEntity
import com.muhammedihsaan.animeexplorer.domain.model.Anime
import com.muhammedihsaan.animeexplorer.domain.model.AnimeDetail

fun AnimeEntity.toDomain(): Anime {
    return Anime(
        id = id,
        title = title,
        episodes = episodes,
        rating = rating,
        imageUrl = imageUrl
    )
}

fun Anime.toEntity(): AnimeEntity {
    return AnimeEntity(
        id = id,
        title = title,
        episodes = episodes,
        rating = rating,
        imageUrl = imageUrl
    )
}

fun AnimeEntity.toDomainDetail(): AnimeDetail {
    return AnimeDetail(
        id = id,
        title = title,
        synopsis = null,
        episodes = episodes,
        rating = rating,
        genres = null,
        videoUrl = null,
        trailerUrl = null,
        imageUrl = imageUrl,
    )
}