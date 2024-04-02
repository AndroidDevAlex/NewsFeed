package com.example.newsfeed.util


sealed class RequestResult<E>(val data: E? = null) {

    class Success<E>(data: E?) : RequestResult<E>(data)

    class Error<E> : RequestResult<E>()

    class Loading<E>(val isLoading: Boolean) : RequestResult<E>(null)
}

fun <In, Out> RequestResult<In>.map(mapper: (In) -> Out): RequestResult<Out> {
    val outData = data?.let { mapper(it) }
    return when (this) {
        is RequestResult.Success -> RequestResult.Success(outData)
        is RequestResult.Error -> RequestResult.Error()
        is RequestResult.Loading -> RequestResult.Loading(false)
    }
}