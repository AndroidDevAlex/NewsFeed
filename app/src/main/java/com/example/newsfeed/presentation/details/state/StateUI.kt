package com.example.newsfeed.presentation.details.state


sealed class StateUI {

    data object Loading : StateUI()

    data object Success : StateUI()
}