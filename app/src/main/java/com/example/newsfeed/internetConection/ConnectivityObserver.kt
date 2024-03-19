package com.example.newsfeed.internetConection

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    val network: Flow<NetworkStatus>
}