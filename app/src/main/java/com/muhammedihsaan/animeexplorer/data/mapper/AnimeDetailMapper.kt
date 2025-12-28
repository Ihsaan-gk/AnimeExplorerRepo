package com.muhammedihsaan.animeexplorer.data.mapper

import com.muhammedihsaan.animeexplorer.data.model.AnimeDetailDto
import com.muhammedihsaan.animeexplorer.domain.model.AnimeDetail

fun AnimeDetailDto.toDomain(): AnimeDetail {
    return AnimeDetail(
        id = mal_id,
        title = title,
        synopsis = synopsis,
        episodes = episodes,
        rating = score,
        genres = genres.map { it.name },
        videoUrl = trailer?.url,
        trailerUrl = trailer?.embed_url,
        imageUrl = images.jpg.image_url
    )
}
