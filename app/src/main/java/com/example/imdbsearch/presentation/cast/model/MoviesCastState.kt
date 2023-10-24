package com.example.imdbsearch.presentation.cast.model

import com.example.imdbsearch.presentation.cast.ui.MovieCastRVItem

sealed interface MoviesCastState {

    object Loading : MoviesCastState

    data class Content(
        val fullTittle: String,
        val items: List<MovieCastRVItem>
    ) : MoviesCastState

    data class Error(
        val message: String,
    ) : MoviesCastState

}