package com.mbds.anneflixproject.models

import com.google.gson.annotations.SerializedName

data class SeriesResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val serie: List<Serie>,
    @SerializedName("total_pages") val pages: Int
)