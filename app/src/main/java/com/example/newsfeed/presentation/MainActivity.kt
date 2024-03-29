package com.example.newsfeed.presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsfeed.R
import com.example.newsfeed.navigation.MainScreen
import com.example.newsfeed.internetConection.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // val wifiOn: Painter = painterResource(id = R.drawable.wifi_on)
            // val wifiOf: Painter = painterResource(id = R.drawable.wifi_off)

            val scaffoldState = rememberScaffoldState()

            val networkStatus by viewModel.isConnected.collectAsState(false)

            if (!networkStatus) {
                LaunchedEffect(false) {

                    scaffoldState.snackbarHostState.showSnackbar(
                        message = getString(R.string.you_are_offline),
                        duration = SnackbarDuration.Short
                    )
                }

            } else {
                LaunchedEffect(true) {

                    scaffoldState.snackbarHostState.showSnackbar(
                        message = getString(R.string.you_are_online),
                        duration = SnackbarDuration.Short
                    )
                }
            }

            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = { host ->
                    SnackbarHost(
                        hostState = host
                    ) { data ->
                        Snackbar(
                            modifier = Modifier.padding(bottom = 60.dp),
                            shape = RoundedCornerShape(10.dp),
                            snackbarData = data
                        )
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$networkStatus")
                }
                MainScreen()
            }
        }
    }
}