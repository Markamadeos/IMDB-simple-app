package com.example.imdbsearch.presentation.details.about.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.models.MovieDetails
import com.example.imdbsearch.presentation.details.about.model.AboutState

class AboutViewModel(private val movieId: String,
                     private val moviesInteractor: MoviesInteractor, ) : ViewModel() {

    private val _stateLiveData = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = _stateLiveData

    init {
        moviesInteractor.getMoviesDetails(movieId, object : MoviesInteractor.MovieDetailsConsumer {

            override fun consume(movieDetails: MovieDetails?, errorMessage: String?) {
                if (movieDetails != null) {
                    _stateLiveData.postValue(AboutState.Content(movieDetails))
                } else {
                    _stateLiveData.postValue(AboutState.Error(errorMessage ?: "Unknown error"))
                }
            }
        })
    }
}