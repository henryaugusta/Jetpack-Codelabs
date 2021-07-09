package com.feylabs.firrieflix.data

sealed class ResponseHelper<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : ResponseHelper<T>()
    class Success<T>(message: String = "", data: T? = null) : ResponseHelper<T>(data)
    class Error<T>(message: String, data: T? = null) : ResponseHelper<T>(data,message)
}