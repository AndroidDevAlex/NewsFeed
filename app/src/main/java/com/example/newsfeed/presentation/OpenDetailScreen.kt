package com.example.newsfeed.presentation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.newsfeed.R
import com.example.newsfeed.ui.theme.DialogButton
import com.example.newsfeed.util.Dimens

@Composable
fun OpenNewsDetailsDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = Dimens.PaddingAll),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = { onConfirm() },
                    colors = ButtonDefaults.buttonColors(DialogButton)
                ) {
                    Text(
                        text = stringResource(R.string.open_details),
                        fontSize = Dimens.FontTextSize,
                        color = Color.Black
                    )
                }
            }
        }
    )
}