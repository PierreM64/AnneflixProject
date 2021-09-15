package com.mbds.anneflixproject.services

import com.mbds.anneflixproject.models.MovieTrailersResponse
import com.mbds.anneflixproject.models.SerieTrailersResponse
import com.mbds.anneflixproject.models.SeriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesRetrofitApiService {

    @GET("tv/popular")
    fun getPopularSeries(
        @Query("api_key") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
        @Query("page") page: Int
    ): Call<SeriesResponse>

    @GET("tv/top_rated")
    fun getTopRatedSeries(
        @Query("api_key") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
        @Query("page") page: Int
    ): Call<SeriesResponse>

    @GET("tv/on_the_air")
    fun getOnAirSeries(
        @Query("api_key") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
        @Query("page") page: Int
    ): Call<SeriesResponse>

    //trailer
    @GET("tv/500/videos") // "movie/500/videos"
    fun getTrailersSerie(
        @Query("api_key") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
    ): Call<SerieTrailersResponse>

}