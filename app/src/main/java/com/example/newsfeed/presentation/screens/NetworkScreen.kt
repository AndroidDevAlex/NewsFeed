package com.example.newsfeed.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsfeed.internetConection.NetworkStatus
import com.example.newsfeed.presentation.viewModel.NetworkViewModel
/*
@Composable
fun NetworkScreen(
    viewModel: NetworkViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState = SnackbarHostState()
) {

    val networkStatus = viewModel.networkStatus.collectAsState() // collectAsStateWithLifecycle()
    if (networkStatus.value == NetworkStatus.Disconnected) {
        LaunchedEffect(networkStatus) {
            snackBarHostState.showSnackbar("you are offline")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Connectivity Service")
        }
    }
}*/