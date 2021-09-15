package com.mbds.anneflixproject.models

import com.google.gson.annotations.SerializedName

data class SerieTrailersResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val serieTrailer: List<SerieTrailers>
)
