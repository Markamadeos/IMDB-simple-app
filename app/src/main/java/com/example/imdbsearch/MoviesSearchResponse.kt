package com.example.imdbsearch

class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<Movie>
)