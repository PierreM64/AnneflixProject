package com.mbds.anneflixproject.services


import com.mbds.anneflixproject.models.MovieTrailersResponse
import com.mbds.anneflixproject.models.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey : String = "2418cf07da66bbe09b9d1b4b86de53c8" ,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8" ,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/120/videos") // "movie/120/videos"
    fun getTrailersMovie(
        @Query("api_key") apiKey: String = "2418cf07da66bbe09b9d1b4b86de53c8",
    ): Call<MovieTrailersResponse>

}