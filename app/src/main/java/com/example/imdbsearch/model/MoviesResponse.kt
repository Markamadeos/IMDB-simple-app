package com.example.imdbsearch.model

class MoviesResponse(
    val searchType: String,
    val expression: String,
    val results: List<Movie>
)