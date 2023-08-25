package com.example.imdbsearch

import com.example.imdbsearch.Movie

class MoviesResponse(
    val searchType: String,
    val expression: String,
    val results: List<Movie>
)