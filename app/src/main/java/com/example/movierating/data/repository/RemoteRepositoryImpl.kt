package com.example.movierating.data.repository

import com.example.movierating.data.model.MoviePage
import com.example.movierating.data.network.ApiService
import retrofit2.Response

class RemoteRepositoryImpl(private val apiService: ApiService) : RemoteRepository {
    override suspend fun getMovieList(
        apiKey: String,
        keyWord: String
    ): Response<MoviePage> =
        apiService.getMoviesList(apiKey = apiKey, query = keyWord)
}
