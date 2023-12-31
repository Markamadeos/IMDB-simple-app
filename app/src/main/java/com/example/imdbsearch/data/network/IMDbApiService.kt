package com.example.imdbsearch.data.network

import com.example.imdbsearch.data.dto.cast.MovieCastResponse
import com.example.imdbsearch.data.dto.detail.MovieDetailsResponse
import com.example.imdbsearch.data.dto.movie.MoviesSearchResponse
import com.example.imdbsearch.data.dto.person.NamesSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IMDbApiService {

    @GET("/en/API/SearchMovie/k_zcuw1ytf/{expression}")
    fun searchMovies(@Path("expression") expression: String): Call<MoviesSearchResponse>

    @GET("/en/API/Title/k_zcuw1ytf/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: String): Call<MovieDetailsResponse>

    @GET("/en/API/FullCast/k_zcuw1ytf/{movie_id}")
    fun getMovieCast(@Path("movie_id") moviewId: String): Call<MovieCastResponse>

    @GET("/en/API/SearchName/k_zcuw1ytf/{expression}")
    fun searchNames(@Path("expression") expression: String): Call<NamesSearchResponse>
}