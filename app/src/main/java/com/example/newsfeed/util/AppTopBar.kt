package com.example.newsfeed.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.example.newsfeed.R
import com.example.newsfeed.ui.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pressBookmark: () -> Unit,// передавать NewsUi
    onBackPress: () -> Unit,
    isBookmarked: Boolean
) {

    val bookmarkIcon: Painter = painterResource(id = R.drawable.bookmark)
    val backIcon: Painter = painterResource(id = R.drawable.back)

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

        navigationIcon = {
            IconButton(
                onClick = { onBackPress() },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    painter = backIcon,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(
                onClick = { pressBookmark() },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                val iconTint = if (isBookmarked) Orange else Color.White
                Icon(
                    painter = bookmarkIcon,
                    contentDescription = "icon",
                    tint = iconTint
                )
            }
        }
    )
}