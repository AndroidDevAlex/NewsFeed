package com.example.newsfeed.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.newsfeed.internetConection.NetworkStatus
import com.example.newsfeed.navigation.MainScreen
//import com.example.newsfeed.presentation.screens.NetworkScreen
import com.example.newsfeed.presentation.viewModel.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NetworkViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // val snackBarHost = remember { SnackbarHostState() }
            //  NetworkScreen(viewModel = viewModel, snackBarHostState = snackBarHost)

            val snackBarHost = remember { SnackbarHostState() }

            val networkStatus by viewModel.networkStatus.collectAsState()

            if (networkStatus == NetworkStatus.Disconnected) {
                LaunchedEffect(networkStatus) {
                    snackBarHost.showSnackbar("You are offline")
                }
            }
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackBarHost) }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$networkStatus")
                }
            }

            MainScreen()
        }
    }
}