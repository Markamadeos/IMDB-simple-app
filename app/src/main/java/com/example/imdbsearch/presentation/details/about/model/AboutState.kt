package com.example.imdbsearch.presentation.details.about.model

import com.example.imdbsearch.domain.models.MovieDetails

sealed interface AboutState {

    data class Content(
        val movie: MovieDetails
    ) : AboutState

    data class Error(
        val message: String
    ) : AboutState

}