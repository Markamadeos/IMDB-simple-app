package com.example.imdbsearch.domain.api

import com.example.imdbsearch.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}