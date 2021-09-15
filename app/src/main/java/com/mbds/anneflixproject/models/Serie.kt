package com.mbds.anneflixproject.models

import com.google.gson.annotations.SerializedName

data class Serie (
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("first_air_date") val firstAirDate: String,
    var isFavorite: Boolean
)