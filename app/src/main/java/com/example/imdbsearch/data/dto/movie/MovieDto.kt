package com.example.imdbsearch.data.dto.movie

data class MovieDto(
    val id: String,
    val resultType: String,
    val image: String,
    val title: String,
    val description: String
)