package com.example.imdbsearch.data.dto.movie

import com.example.imdbsearch.data.dto.Response

class MoviesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>
) : Response()