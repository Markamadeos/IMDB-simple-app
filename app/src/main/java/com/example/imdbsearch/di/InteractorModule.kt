package com.example.imdbsearch.di

import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(repository = get())
    }
}