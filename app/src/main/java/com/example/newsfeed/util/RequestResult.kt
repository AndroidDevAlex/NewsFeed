package com.example.newsfeed.util


sealed class RequestResult<E>(val data: E? = null) {

    class Success<E>(data: E) : RequestResult<E>(data)

    class Error<E> : RequestResult<E>()

    class Loading<E>(val isLoading: Boolean) : RequestResult<E>(null)
}