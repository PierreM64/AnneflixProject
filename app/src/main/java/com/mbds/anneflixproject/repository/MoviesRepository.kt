package com.mbds.anneflixproject.repository

import android.util.Log
import com.mbds.anneflixproject.models.*
import com.mbds.anneflixproject.services.RetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    private val api: RetrofitApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RetrofitApiService::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ){
        api.getPopularMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                            //Log.d("Repository", "Movies: ${responseBody.movies}")
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                    //Log.e("Repository", "Failure", t)
                }
            })
    }

    //---
    fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopRatedMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
    //---

    fun getNowPlayingMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getNowPlayingMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
    //---
    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getUpcomingMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    //---
    fun getTrailersMovie(
        onSuccess: (movieTrailers: List<MovieTrailers>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTrailersMovie()
            .enqueue(object : Callback<MovieTrailersResponse> {
                override fun onResponse(
                    call: Call<MovieTrailersResponse>,
                    response: Response<MovieTrailersResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movieTrailer)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<MovieTrailersResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    /*--- Favorite
    fun updateFavoriteMovieStatus(movie: Movie, status: Boolean) {
        movie.isFavorite = status
    }*/

}