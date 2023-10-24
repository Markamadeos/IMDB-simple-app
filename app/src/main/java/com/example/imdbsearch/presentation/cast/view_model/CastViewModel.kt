package com.example.imdbsearch.presentation.cast.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.models.MovieCast
import com.example.imdbsearch.presentation.cast.model.MoviesCastState
import com.example.imdbsearch.presentation.cast.ui.MovieCastRVItem

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
        stateLiveData.postValue(MoviesCastState.Loading)

        moviesInteractor.getMovieCast(movieId, object : MoviesInteractor.MovieCastConsumer {

            override fun consume(movieCast: MovieCast?, errorMessage: String?) {
                if (movieCast != null) {
                    // добавляем конвертацию в UiState
                    stateLiveData.postValue(castToUiStateContent(movieCast))
                } else {
                    stateLiveData.postValue(MoviesCastState.Error(errorMessage ?: "Unknown error"))
                }
            }

        })
    }

    private fun castToUiStateContent(cast: MovieCast): MoviesCastState {
        // Строим список элементов RecyclerView
        val items = buildList<MovieCastRVItem> {
            // Если есть хотя бы один режиссёр, добавим заголовок
            if (cast.directors.isNotEmpty()) {
                this += MovieCastRVItem.HeaderItem("Directors")
                this += cast.directors.map { MovieCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один сценарист, добавим заголовок
            if (cast.writers.isNotEmpty()) {
                this += MovieCastRVItem.HeaderItem("Writers")
                this += cast.writers.map { MovieCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один актёр, добавим заголовок
            if (cast.actors.isNotEmpty()) {
                this += MovieCastRVItem.HeaderItem("Actors")
                this += cast.actors.map { MovieCastRVItem.PersonItem(it) }
            }

            // Если есть хотя бы один дополнительный участник, добавим заголовок
            if (cast.others.isNotEmpty()) {
                this += MovieCastRVItem.HeaderItem("Others")
                this += cast.others.map { MovieCastRVItem.PersonItem(it) }
            }
        }


        return MoviesCastState.Content(
            cast.fullTitle!!,
            items = items
        )
    }

}