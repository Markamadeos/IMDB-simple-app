package com.example.imdbsearch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {

    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun getMovie(@Path("expression") expression: String): Call<MoviesSearchResponse>
    abstract fun searchMovies(toString: String): Any
}