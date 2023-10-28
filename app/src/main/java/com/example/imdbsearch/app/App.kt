package com.example.imdbsearch.app

import android.app.Application
import com.example.imdbsearch.di.dataModule
import com.example.imdbsearch.di.interactorModule
import com.example.imdbsearch.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import viewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                repositoryModule,
                interactorModule,
                viewModelModule
            )
        }
    }
}