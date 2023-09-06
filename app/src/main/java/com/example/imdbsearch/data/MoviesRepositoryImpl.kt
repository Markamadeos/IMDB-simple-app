package com.example.imdbsearch.data

import com.example.imdbsearch.data.dto.MoviesSearchRequest
import com.example.imdbsearch.data.dto.MoviesSearchResponse
import com.example.imdbsearch.domain.api.MoviesRepository
import com.example.imdbsearch.domain.models.Movie
import com.example.imdbsearch.util.Resource
import java.net.HttpURLConnection

class MoviesRepositoryImpl(private val networkClient: NetworkClient): MoviesRepository {
    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            HttpURLConnection.HTTP_OK -> {
                Resource.Success((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)})
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}