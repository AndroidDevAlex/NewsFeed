package com.example.newsfeed.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.newsfeed.R
import com.example.newsfeed.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(pressClicked: () -> Unit) {

    val bookmarkIcon: Painter = painterResource(id = R.drawable.bookmark)

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                fontSize = Dimens.TopAppBarFontSize,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }, colors = TopAppBarDefaults.smallTopAppBarColors(Orange),

        actions = {
            IconButton(
                onClick = { pressClicked() }
            ) {
                Icon(
                    painter = bookmarkIcon,
                    contentDescription = "icon",
                    tint = Color.DarkGray
                )
            }
        }
    )
}