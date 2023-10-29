package com.example.imdbsearch.di

import com.example.imdbsearch.data.MoviesRepositoryImpl
import com.example.imdbsearch.data.NamesRepositoryImpl
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.api.NamesRepository
import com.example.imdbsearch.util.MovieCastConverter
import org.koin.dsl.module

val repositoryModule = module {

    factory { MovieCastConverter() }

    single<MoviesRepository> {
        MoviesRepositoryImpl(
            networkClient = get(),
            localStorage = get(),
            movieCastConverter = get()
        )
    }

    single<NamesRepository> {
        NamesRepositoryImpl(networkClient = get())
    }

}