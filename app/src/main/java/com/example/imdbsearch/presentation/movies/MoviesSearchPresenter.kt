package com.example.imdbsearch.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.imdbsearch.R
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.models.Movie
import com.example.imdbsearch.ui.model.MoviesState
import com.example.imdbsearch.util.Creator

class MoviesSearchPresenter(
    private val context: Context
) {

    private var view: MoviesView? = null
    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val movies = ArrayList<Movie>()
    private val handler = Handler(Looper.getMainLooper())
    private var lastSearchText: String? = null
    private var state: MoviesState? = null

    fun attachView(view: MoviesView) {
        this.view = view
        state?.let { view.render(it) }
    }

    fun detachView() {
        this.view = null
    }

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }
        this.lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY_MS)
    }


    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        val movies = mutableListOf<Movie>()
                        if (foundMovies != null) {
                            movies.addAll(foundMovies)
                        }

                        when {
                            errorMessage != null -> {
                                renderState(
                                    MoviesState.Error(
                                        context.getString(R.string.something_went_wrong),
                                    )
                                )
                                view?.showToast(errorMessage)
                            }

                            movies.isEmpty() -> {
                                renderState(
                                    MoviesState.Empty(
                                        context.getString(R.string.nothing_found),
                                    )
                                )
                            }

                            else -> {
                                renderState(
                                    MoviesState.Content(
                                        movies = movies,
                                    )
                                )
                            }
                        }

                    }
                }
            })
        }
    }

    private fun renderState(state: MoviesState) {
        this.state = state
        this.view?.render(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MS = 2000L
    }
}