package com.example.imdbsearch.di

import android.content.Context
import com.example.imdbsearch.data.NetworkClient
import com.example.imdbsearch.data.network.IMDbApiService
import com.example.imdbsearch.data.network.RetrofitNetworkClient
import com.example.imdbsearch.data.storage.LocalStorage
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<IMDbApiService> {
        Retrofit.Builder()
            .baseUrl("https://imdb-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IMDbApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single<LocalStorage> {
        LocalStorage(sharedPreferences = get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(imdbService = get(), context = androidContext())
    }

}