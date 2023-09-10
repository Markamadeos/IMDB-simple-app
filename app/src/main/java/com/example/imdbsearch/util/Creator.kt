package com.example.imdbsearch.util

import android.app.Activity
import android.content.Context
import com.example.imdbsearch.data.MoviesRepositoryImpl
import com.example.imdbsearch.data.network.RetrofitNetworkClient
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.impl.MoviesInteractorImpl
import com.example.imdbsearch.presentation.movies.MoviesSearchPresenter
import com.example.imdbsearch.presentation.PosterController
import com.example.imdbsearch.presentation.movies.MoviesView
import com.example.imdbsearch.ui.movies.MovieAdapter

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(
        view: MoviesView,
        adapter: MovieAdapter,
        context: Context
    ): MoviesSearchPresenter {
        return MoviesSearchPresenter(view, adapter, context)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}