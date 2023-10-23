package com.example.imdbsearch.domain.impl

import com.example.imdbsearch.domain.api.MoviesInteractor
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.models.Movie
import com.example.imdbsearch.util.Resource
import java.util.concurrent.Executors

class MoviesInteractorImpl(private val repository: MoviesRepository) : MoviesInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchMovies(expression: String, consumer: MoviesInteractor.MoviesConsumer) {
        executor.execute {
            when (val resource = repository.searchMovies(expression)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        repository.addMovieToFavorites(movie)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        repository.removeMovieFromFavorites(movie)
    }

    override fun getMoviesDetails(
        movieId: String,
        consumer: MoviesInteractor.MovieDetailsConsumer
    ) {
        executor.execute {
            when (val resource = repository.getMovieDetails(movieId)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(resource.data, resource.message)
                }
            }
        }
    }

    override fun getMovieCast(movieId: String, consumer: MoviesInteractor.MovieCastConsumer) {
        executor.execute {
            when (val resource = repository.getMovieCast(movieId = movieId)) {
                is Resource.Success -> consumer.consume(
                    movieCast = resource.data,
                    errorMessage = null
                )

                is Resource.Error -> consumer.consume(
                    movieCast = resource.data,
                    errorMessage = resource.message
                )
            }
        }
    }
}
