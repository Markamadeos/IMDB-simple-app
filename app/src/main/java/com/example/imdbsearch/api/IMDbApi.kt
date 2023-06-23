package com.example.imdbsearch.api

import com.example.imdbsearch.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApi {

    @GET("/en/API/SearchMovie/k_ohb33ui7/{expression}")
    fun getMovie(@Path("expression") expression: String): Call<MoviesResponse>
}