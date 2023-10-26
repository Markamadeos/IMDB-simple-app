package com.example.imdbsearch.di

import com.example.imdbsearch.core.navigation.api.Router
import com.example.imdbsearch.core.navigation.impl.RouterImpl
import org.koin.dsl.module

val navigationModule = module {

    val router = RouterImpl()

    single<Router> { router }
    single { router.navigatorHolder }
}