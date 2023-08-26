package com.example.imdbsearch

import com.example.imdbsearch.data.dto.MoviesRepositoryImpl
import com.example.imdbsearch.data.network.RetrofitNetworkClient
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.impl.MoviesInteractorImpl

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }
}