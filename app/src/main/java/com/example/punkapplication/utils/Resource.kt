package com.example.punkapplication.utils

sealed class Resource<T> {
    data class Loading<T>(val data: T? = null): Resource<T>()
    data class Success<T>(val data: T? = null): Resource<T>()
    data class Error<T>(val message: String? = null, val defaultData: T? = null): Resource<T>()
}