package com.example.imdbsearch.data.dto

import com.example.imdbsearch.data.NetworkClient
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.models.Movie

class MoviesRepositoryImpl(private val networkClient: NetworkClient): MoviesRepository {
    override fun searchMovies(expression: String): List<Movie> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return if (response.resultCode == 200) {
            (response as MoviesSearchResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description) }
        } else {
            emptyList()
        }
    }
}