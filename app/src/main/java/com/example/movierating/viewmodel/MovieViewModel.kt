package com.example.movierating.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movierating.data.model.MoviePage
import com.example.movierating.data.network.Resource
import com.example.movierating.data.repository.AppRemoteRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val remoteRepository: AppRemoteRepository) : ViewModel() {

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
                movieListLiveData.postValue(response)
            }
        } catch (e: Exception) {
            Log.e(MovieViewModel::class.java.simpleName, e.localizedMessage, e)
        }
    }

    fun getMovies(): MutableLiveData<Resource<MoviePage>> {
        return movieListLiveData
    }
}
