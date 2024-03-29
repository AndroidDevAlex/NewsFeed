package com.example.newsfeed.internetConection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    val isConnected: Flow<Boolean> = networkConnectivityObserver.isConnected
}