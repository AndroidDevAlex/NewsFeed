package com.example.newsfeed.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeed.internetConection.NetworkConnectivityObserver
import com.example.newsfeed.internetConection.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    val networkStatus: StateFlow<NetworkStatus> = networkConnectivityObserver.network.stateIn(
        initialValue = NetworkStatus.Unknown,
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )
}