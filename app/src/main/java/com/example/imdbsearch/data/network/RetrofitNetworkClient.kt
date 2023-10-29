package com.example.imdbsearch.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.imdbsearch.data.NetworkClient
import com.example.imdbsearch.data.dto.movie.MoviesSearchRequest
import com.example.imdbsearch.data.dto.Response
import com.example.imdbsearch.data.dto.cast.MovieCastRequest
import com.example.imdbsearch.data.dto.detail.MovieDetailsRequest
import com.example.imdbsearch.data.dto.person.NamesSearchRequest
import java.net.HttpURLConnection

class RetrofitNetworkClient(private val imdbService: IMDbApiService, private val context: Context) :
    NetworkClient {

    override fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        if ((dto !is MoviesSearchRequest)
            && (dto !is MovieDetailsRequest)
            && (dto !is MovieCastRequest)
            && (dto !is NamesSearchRequest)
        ) {
            return Response().apply { resultCode = HttpURLConnection.HTTP_BAD_REQUEST }
        }

        val response = when (dto) {
            is MoviesSearchRequest -> imdbService.searchMovies(dto.expression).execute()
            is MovieDetailsRequest -> imdbService.getMovieDetails(dto.movieId).execute()
            is NamesSearchRequest -> imdbService.searchNames(dto.expression).execute()
            else -> imdbService.getMovieCast((dto as MovieCastRequest).movieId).execute()
        }

        val body = response.body()
        return body?.apply { resultCode = response.code() } ?: Response().apply {
            resultCode = response.code()
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}