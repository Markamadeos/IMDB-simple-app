package com.example.imdbsearch.domain.api

import com.example.imdbsearch.domain.models.Movie
import com.example.imdbsearch.domain.models.MovieDetails
import com.example.imdbsearch.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
    fun getMovieDetails(movieId: String): Resource<MovieDetails>
}
