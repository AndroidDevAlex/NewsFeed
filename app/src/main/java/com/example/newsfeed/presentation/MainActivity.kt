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
import androidx.compose.ui.graphics.Color
import com.example.newsfeed.R
import com.example.newsfeed.navigation.MainScreen
import com.example.newsfeed.internetConection.NetworkViewModel
import com.example.newsfeed.ui.theme.Blue
import com.example.newsfeed.ui.theme.Grey
import com.example.newsfeed.util.Dimens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: NetworkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val scaffoldState = rememberScaffoldState()

            val networkStatus by viewModel.isConnected.collectAsState()

            if (!networkStatus) {
                LaunchedEffect(false) {

                    scaffoldState.snackbarHostState.showSnackbar(
                        message = getString(R.string.you_are_offline),
                        duration = SnackbarDuration.Indefinite
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
                        val backgroundColor = if (networkStatus) Blue else Grey
                        Snackbar(
                            modifier = Modifier.padding(bottom = Dimens.PaddingBottomModifier),
                            shape = RoundedCornerShape(Dimens.PaddingModifier),
                            backgroundColor = backgroundColor,
                            snackbarData = data,
                            contentColor = Color.White
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