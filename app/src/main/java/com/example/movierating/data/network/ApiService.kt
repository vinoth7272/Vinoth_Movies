
package com.example.movierating.data.network

import com.example.movierating.data.model.MoviePage
import com.example.movierating.util.Constant.LANGUAGE
import com.example.movierating.util.Constant.SEARCH_MOVIE
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(SEARCH_MOVIE)
    suspend fun getMoviesList(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = LANGUAGE,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") adult: Boolean = false,
    ): Response<MoviePage>
}
