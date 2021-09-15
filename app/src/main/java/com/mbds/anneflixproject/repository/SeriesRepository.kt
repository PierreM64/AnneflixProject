package com.mbds.anneflixproject.repository

import com.mbds.anneflixproject.models.*
import com.mbds.anneflixproject.services.SeriesRetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SeriesRepository {

    private val apiSeries: SeriesRetrofitApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiSeries = retrofit.create(SeriesRetrofitApiService::class.java)
    }

    //---
    fun getPopularSeries(
        page: Int = 1,
        onSuccess: (serie: List<Serie>) -> Unit,
        onError: () -> Unit
    ) {
        SeriesRepository.apiSeries.getPopularSeries(page = page)
            .enqueue(object : Callback<SeriesResponse> {
                override fun onResponse(
                    call: Call<SeriesResponse>,
                    response: Response<SeriesResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.serie)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    //---
    fun getTopRatedSeries(
        page: Int = 1,
        onSuccess: (serie: List<Serie>) -> Unit,
        onError: () -> Unit
    ) {
        SeriesRepository.apiSeries.getTopRatedSeries(page = page)
            .enqueue(object : Callback<SeriesResponse> {
                override fun onResponse(
                    call: Call<SeriesResponse>,
                    response: Response<SeriesResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.serie)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    //---
    fun getOnAirSeries(
        page: Int = 1,
        onSuccess: (serie: List<Serie>) -> Unit,
        onError: () -> Unit
    ) {
        SeriesRepository.apiSeries.getOnAirSeries(page = page)
            .enqueue(object : Callback<SeriesResponse> {
                override fun onResponse(
                    call: Call<SeriesResponse>,
                    response: Response<SeriesResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.serie)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<SeriesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    //---
    fun getTrailersSerie(
        onSuccess: (serieTrailers: List<SerieTrailers>) -> Unit,
        onError: () -> Unit
    ) {
        SeriesRepository.apiSeries.getTrailersSerie()
            .enqueue(object : Callback<SerieTrailersResponse> {
                override fun onResponse(
                    call: Call<SerieTrailersResponse>,
                    response: Response<SerieTrailersResponse>
                ) {
                    if (response.isSuccessful) {

                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.serieTrailer)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<SerieTrailersResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

}