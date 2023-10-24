package com.example.imdbsearch.presentation.cast.ui

import com.example.imdbsearch.domain.models.MovieCastPerson

sealed interface MovieCastRVItem {

    data class HeaderItem(
        val headerText: String
    ) : MovieCastRVItem

    data class PersonItem(
        val data: MovieCastPerson
    ) : MovieCastRVItem

}