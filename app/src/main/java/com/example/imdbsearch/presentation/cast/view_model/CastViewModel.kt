package com.example.imdbsearch.presentation.cast.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.models.MovieCast
import com.example.imdbsearch.presentation.cast.model.MoviesCastState

// В конструктор пробросили необходимые для запроса параметры
class MoviesCastViewModel(
    private val movieId: String,
    private val moviesInteractor: MoviesInteractor,
) : ViewModel() {

    // Стандартная обвязка для определения State
    // и наблюдения за ним в UI-слое
    private val stateLiveData = MutableLiveData<MoviesCastState>()
    fun observeState(): LiveData<MoviesCastState> = stateLiveData

    init {
        // При старте экрана покажем ProgressBar
        stateLiveData.postValue(MoviesCastState.Loading)

        // Выполняем сетевой запрос
        moviesInteractor.getMovieCast(movieId, object : MoviesInteractor.MovieCastConsumer {

            // Обрабатываем результат этого запроса
            override fun consume(movieCast: MovieCast?, errorMessage: String?) {
                if (movieCast != null) {
                    stateLiveData.postValue(MoviesCastState.Content(movieCast))
                } else {
                    stateLiveData.postValue(MoviesCastState.Error(errorMessage ?: "Unknown error"))
                }
            }

        })
    }
}