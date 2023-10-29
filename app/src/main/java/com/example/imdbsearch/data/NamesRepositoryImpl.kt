package com.example.imdbsearch.data


import com.example.imdbsearch.data.dto.person.NamesSearchRequest
import com.example.imdbsearch.data.dto.person.NamesSearchResponse
import com.example.imdbsearch.domain.api.NamesRepository
import com.example.imdbsearch.domain.models.Person
import com.example.imdbsearch.util.Resource
import javax.net.ssl.HttpsURLConnection

class NamesRepositoryImpl(private val networkClient: NetworkClient) : NamesRepository {

    override fun searchNames(expression: String): Resource<List<Person>> {
        val response = networkClient.doRequest(NamesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            HttpsURLConnection.HTTP_OK -> {
                with(response as NamesSearchResponse) {
                    Resource.Success(results.map {
                        Person(
                            id = it.id,
                            name = it.title,
                            description = it.description,
                            photoUrl = it.image
                        )
                    })
                }
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}