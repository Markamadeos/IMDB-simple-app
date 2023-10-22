package com.example.imdbsearch.data

import com.example.imdbsearch.data.dto.detail.MovieDetailsRequest
import com.example.imdbsearch.data.dto.detail.MovieDetailsResponse
import com.example.imdbsearch.data.dto.movie.MoviesSearchRequest
import com.example.imdbsearch.data.dto.movie.MoviesSearchResponse
import com.example.imdbsearch.data.storage.LocalStorage
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.models.Movie
import com.example.imdbsearch.domain.models.MovieDetails
import com.example.imdbsearch.util.Resource
import java.net.HttpURLConnection

class MoviesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val localStorage: LocalStorage
) : MoviesRepository {
    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            HttpURLConnection.HTTP_OK -> {
                val stored = localStorage.getSavedFavorites()
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(
                        it.id,
                        it.resultType,
                        it.image,
                        it.title,
                        it.description,
                        stored.contains(it.id)
                    )
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) {
        localStorage.addToFavorites(movie.id)
    }

    override fun removeMovieFromFavorites(movie: Movie) {
        localStorage.removeFromFavorites(movie.id)
    }

    override fun getMovieDetails(movieId: String): Resource<MovieDetails> {
        val response = networkClient.doRequest(MovieDetailsRequest(movieId))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            HttpURLConnection.HTTP_OK -> {
                with(response as MovieDetailsResponse) {
                    Resource.Success(
                        MovieDetails(
                            id, title, imDbRating, year,
                            countries, genres, directors, writers, stars, plot
                        )
                    )
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")

            }
        }
    }
}