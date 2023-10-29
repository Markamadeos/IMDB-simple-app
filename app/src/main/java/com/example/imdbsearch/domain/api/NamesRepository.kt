package com.example.imdbsearch.domain.api

import com.example.imdbsearch.domain.models.Person
import com.example.imdbsearch.util.Resource

interface NamesRepository {
    fun searchNames(expression: String): Resource<List<Person>>
}