package com.example.imdbsearch.data.dto

import com.example.imdbsearch.domain.models.Movie

class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>
) : Response()