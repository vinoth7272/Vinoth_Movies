package com.example.movierating.data.repository

import com.example.movierating.data.model.MoviePage
import retrofit2.Response

interface RemoteRepository {

    suspend fun getMovieList(
        apiKey: String,
        keyWord: String
    ): Response<MoviePage>
}
