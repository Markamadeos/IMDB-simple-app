package com.example.imdbsearch.presentation.details.poster.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PosterViewModel(posterUrl: String) : ViewModel() {

    private val _urlLiveData = MutableLiveData(posterUrl)

    fun observeUrl(): LiveData<String> = _urlLiveData
}