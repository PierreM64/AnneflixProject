package com.mbds.anneflixproject.models

data class Favorite (
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
    val rating: Float,
    val releaseDate: String,
    val type: FavoriteType
)
