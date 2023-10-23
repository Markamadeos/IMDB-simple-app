package com.example.imdbsearch.presentation.cast.model

import com.example.imdbsearch.domain.models.MovieCast

sealed interface MoviesCastState {

    object Loading : MoviesCastState

    data class Content(
        val movie: MovieCast,
    ) : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState

}