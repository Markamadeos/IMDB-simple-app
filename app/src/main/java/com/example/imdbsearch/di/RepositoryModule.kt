package com.example.imdbsearch.di

import com.example.imdbsearch.data.MoviesRepositoryImpl
import com.example.imdbsearch.domain.api.MoviesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(networkClient = get(), localStorage = get())
    }

}