package com.example.imdbsearch.presentation.poster

class PosterPresenter(private val view: PosterView, private val url: String) {

    fun onCreate() {
        view.setupPosterImage(url)
    }

}