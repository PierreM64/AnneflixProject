package com.mbds.anneflixproject.models

import com.google.gson.annotations.SerializedName

data class SerieTrailers(
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("official") val official: Boolean,
    @SerializedName("published_at") val published_at : String

)
