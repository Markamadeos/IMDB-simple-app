package com.example.imdbsearch.ui.cast

import com.example.imdbsearch.domain.models.MovieCastPerson
import com.example.imdbsearch.core.ui.RVItem

sealed interface MovieCastRVItem : RVItem {

    data class HeaderItem(
        val headerText: String
    ) : MovieCastRVItem

    data class PersonItem(
        val data: MovieCastPerson
    ) : MovieCastRVItem

}