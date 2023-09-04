package com.example.imdbsearch

import android.app.Activity
import com.example.imdbsearch.data.MoviesRepositoryImpl
import com.example.imdbsearch.data.network.RetrofitNetworkClient
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.impl.MoviesInteractorImpl
import com.example.imdbsearch.presentation.MoviesSearchController
import com.example.imdbsearch.presentation.PosterController
import com.example.imdbsearch.ui.movies.MovieAdapter

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }

    fun provideMoviesSearchController(
        activity: Activity,
        adapter: MovieAdapter
    ): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}