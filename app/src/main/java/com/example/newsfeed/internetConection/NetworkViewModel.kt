package com.example.newsfeed.internetConection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver,
) : ViewModel() {

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected

    init {
        observeConnection()
    }

    private fun observeConnection() {
        viewModelScope.launch {
            networkConnectivityObserver.isConnected.collect { isConnected ->
                _isConnected.value = isConnected
            }
        }
    }
}