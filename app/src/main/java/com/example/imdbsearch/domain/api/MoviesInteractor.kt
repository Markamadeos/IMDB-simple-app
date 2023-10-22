package com.example.imdbsearch.domain.api

import com.example.imdbsearch.domain.models.Movie
import com.example.imdbsearch.domain.models.MovieDetails

interface MoviesInteractor {
    fun searchMovies(expression: String, consumer: MoviesConsumer)

    interface MoviesConsumer {
        fun consume(foundMovies: List<Movie>?, errorMessage: String?)
    }

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

    fun getMoviesDetails(movieId: String, consumer: MovieDetailsConsumer)
    interface MovieDetailsConsumer {
        fun consume(movieDetails: MovieDetails?, errorMessage: String?)
    }
}