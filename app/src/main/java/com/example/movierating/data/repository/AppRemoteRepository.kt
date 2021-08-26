package com.example.movierating.data.repository

import android.util.Log
import com.example.movierating.data.model.MoviePage
import com.example.movierating.data.network.ApiService
import com.example.movierating.data.network.Resource
import retrofit2.HttpException
import retrofit2.Response

class AppRemoteRepository(private val apiService: ApiService) : BaseRepository() {

    companion object {
        private val TAG = AppRemoteRepository::class.java.name
        const val GENERAL_ERROR_CODE = 499
    }

    suspend fun getMovieList(
        apiKey: String,
        keyWord: String
    ): Resource<MoviePage> {
        var result: Resource<MoviePage> = handleSuccess(MoviePage())
        try {
            val response = apiService.getMoviesList(apiKey = apiKey, query = keyWord)
            response.let {
                it.body()?.let { moviePage ->
                    result = handleSuccess(moviePage)
                }
                it.errorBody()?.let { responseErrorBody ->
                    if (responseErrorBody is HttpException) {
                        responseErrorBody.response()?.code()?.let { errorCode ->
                            result = handleException(errorCode)
                        }
                    } else result = handleException(GENERAL_ERROR_CODE)
                }
            }
        } catch (error: HttpException) {
            Log.e("$TAG ","- Error: ${error.message}")
            return handleException(error.code())
        }
        return result
    }
}
