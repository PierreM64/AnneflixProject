package com.mbds.anneflixproject.models

import com.google.gson.annotations.SerializedName

data class MovieTrailersResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("results") val movieTrailer: List<MovieTrailers>
)
