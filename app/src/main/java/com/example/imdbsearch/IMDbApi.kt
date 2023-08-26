package com.example.imdbsearch

import com.example.imdbsearch.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApi {

    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun getMovie(@Path("expression") expression: String): Call<MoviesResponse>
    abstract fun searchMovies(toString: String): Any
}