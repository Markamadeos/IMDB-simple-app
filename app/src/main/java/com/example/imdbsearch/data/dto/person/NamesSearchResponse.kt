package com.example.imdbsearch.data.dto.person

import com.example.imdbsearch.data.dto.Response

class NamesSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<PersonDto>
) : Response()