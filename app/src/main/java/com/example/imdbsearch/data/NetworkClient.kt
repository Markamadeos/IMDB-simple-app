package com.example.imdbsearch.data

import com.example.imdbsearch.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}