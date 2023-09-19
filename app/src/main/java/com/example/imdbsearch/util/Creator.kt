package com.example.imdbsearch.util

import android.content.Context
import com.example.imdbsearch.data.MoviesRepositoryImpl
import com.example.imdbsearch.data.network.RetrofitNetworkClient
import com.example.imdbsearch.data.storage.LocalStorage
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.impl.MoviesInteractorImpl
import com.example.imdbsearch.presentation.poster.PosterPresenter
import com.example.imdbsearch.presentation.poster.PosterView

object Creator {
    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun providePosterPresenter(view: PosterView, url: String): PosterPresenter {
        return PosterPresenter(view, url)
    }

    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(
            RetrofitNetworkClient(context),
            LocalStorage(context.getSharedPreferences("local_storage", Context.MODE_PRIVATE)),
        )
    }
}