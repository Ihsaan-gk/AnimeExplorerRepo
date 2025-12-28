package com.muhammedihsaan.animeexplorer.data.mapper

import com.muhammedihsaan.animeexplorer.data.model.AnimeDto
import com.muhammedihsaan.animeexplorer.domain.model.Anime

fun AnimeDto.toDomain(): Anime {
    return Anime(
        id = mal_id,
        title = title,
        episodes = episodes,
        rating = score,
        imageUrl = images.jpg.image_url
    )
}