
package com.example.movierating.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierating.data.model.MoviePage
import com.example.movierating.data.network.NetworkUtils
import com.example.movierating.data.network.Resource
import com.example.movierating.data.repository.RemoteRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val remoteRepository: RemoteRepository) : ViewModel() {

    private val movieListLiveData = MutableLiveData<Resource<MoviePage>>()

    /**
     * used to fetch the movies list
     * @param apiKey key to authenticate the user
     */
    fun fetchMovies(apiKey: String, keyWord: String) {
        try {
            viewModelScope.launch {
                movieListLiveData.postValue(Resource.loading(null))
                val response = remoteRepository.getMovieList(apiKey = apiKey, keyWord = keyWord)
                Log.d(MovieViewModel::class.java.simpleName, "getMovieList -> ${response.isSuccessful}")
                if (response.isSuccessful) {
                    movieListLiveData.postValue(Resource.success(response.body()))
                } else {
                    movieListLiveData.postValue(
                        Resource.error(
                            NetworkUtils.getNetworkError(
                                response.code(),
                                response.message()
                            )
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(MovieViewModel::class.java.simpleName, e.localizedMessage, e)
        }
    }

    fun getMovies(): MutableLiveData<Resource<MoviePage>> {
        return movieListLiveData
    }
}
