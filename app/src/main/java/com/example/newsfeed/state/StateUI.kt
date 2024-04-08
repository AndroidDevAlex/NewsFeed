package com.example.newsfeed.state

import com.example.newsfeed.presentation.NewsUi
import com.example.newsfeed.util.RequestResult

sealed class StateUI {

    object None : StateUI()

    class Loading(val news: List<NewsUi>?) : StateUI()

    class Error(val news: List<NewsUi>?) : StateUI()

    class Success(val news: List<NewsUi>) : StateUI()
}

fun RequestResult<List<NewsUi>>.toStateUI(): StateUI {
    return when (this) {
        is RequestResult.Error -> StateUI.Error(data)
        is RequestResult.Success -> StateUI.Success(checkNotNull(data))
        is RequestResult.Loading -> StateUI.Loading(data)
    }
}