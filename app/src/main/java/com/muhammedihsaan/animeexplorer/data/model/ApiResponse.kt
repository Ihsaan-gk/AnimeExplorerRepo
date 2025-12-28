package com.muhammedihsaan.animeexplorer.data.model

data class ApiResponse<T>(
    val data: T,
    val pagination: PaginationDto? = null
)