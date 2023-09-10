package com.example.imdbsearch.presentation.movies

import com.example.imdbsearch.ui.model.MoviesState

interface MoviesView {
    fun render(state: MoviesState)

    fun showToast(message: String)
}