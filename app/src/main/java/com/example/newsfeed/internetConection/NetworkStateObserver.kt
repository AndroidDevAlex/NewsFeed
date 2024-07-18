package com.example.newsfeed.internetConection

import kotlinx.coroutines.flow.StateFlow

interface NetworkStateObserver {
    val isConnected: StateFlow<Boolean>
}