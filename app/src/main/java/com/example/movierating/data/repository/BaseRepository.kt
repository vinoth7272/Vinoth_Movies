package com.example.movierating.data.repository

import com.example.movierating.data.network.NetworkUtils.getErrorMessage
import com.example.movierating.data.network.Resource

abstract class BaseRepository {

    companion object {

        fun <T : Any> handleSuccess(data: T): Resource<T> {
            return Resource.success(data)
        }

        fun <T : Any> handleException(code: Int): Resource<T> {
            val exception = getErrorMessage(code)
            return Resource.error(Exception(exception).localizedMessage)
        }

    }
}