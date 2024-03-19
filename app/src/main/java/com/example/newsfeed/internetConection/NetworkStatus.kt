package com.example.newsfeed.internetConection

sealed class NetworkStatus {

    object Unknown: NetworkStatus()

    object Lost: NetworkStatus()

    object Connected: NetworkStatus()

    object Disconnected: NetworkStatus()
}