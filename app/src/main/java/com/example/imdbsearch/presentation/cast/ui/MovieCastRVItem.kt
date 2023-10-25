package com.example.imdbsearch.presentation.cast.ui

import com.example.imdbsearch.domain.models.MovieCastPerson
import com.example.imdbsearch.presentation.core.ui.RVItem

sealed interface MovieCastRVItem : RVItem {

    data class HeaderItem(
        val headerText: String
    ) : MovieCastRVItem

    data class PersonItem(
        val data: MovieCastPerson
    ) : MovieCastRVItem

}