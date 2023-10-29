package com.example.imdbsearch.di

import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.api.NamesInteractor
import com.example.imdbsearch.domain.impl.MoviesInteractorImpl
import com.example.imdbsearch.domain.impl.NamesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<MoviesInteractor> {
        MoviesInteractorImpl(repository = get())
    }

    single<NamesInteractor> {
        NamesInteractorImpl(repository = get())
    }
}