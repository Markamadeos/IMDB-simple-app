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
    private val view: MoviesView,
    private val context: Context
) {

    private val moviesInteractor = Creator.provideMoviesInteractor(context)

    private val movies = ArrayList<Movie>()

    private val handler = Handler(Looper.getMainLooper())

    private var lastSearchText: String? = null

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }


    fun searchDebounce(changedText: String) {
        this.lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY_MS)
    }


    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            view.render(
                MoviesState.Loading
            )

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }

                        when {
                            errorMessage != null -> {
                                view.render(
                                    MoviesState.Error(
                                        errorMessage = context.getString(R.string.something_went_wrong),
                                    )
                                )
                                view.showToast(errorMessage)
                            }

                            movies.isEmpty() -> {
                                view.render(
                                    MoviesState.Empty(
                                        message = context.getString(R.string.nothing_found),
                                    )
                                )
                            }

                            else -> {
                                view.render(
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

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MS = 2000L
    }
}