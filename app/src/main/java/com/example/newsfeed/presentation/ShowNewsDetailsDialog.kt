package com.example.newsfeed.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import com.example.newsfeed.presentation.entityUi.ItemNewsUi
import java.net.URLEncoder

@Composable
fun ShowNewsDetailsDialog(
    isDialogVisible: Boolean,
    selectedNews: ItemNewsUi?,
    isConnected: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    context: Context
) {

    if (isDialogVisible && selectedNews != null) {
        OpenNewsDetailsDialog(
            onDismiss = { onDismiss() },
            onConfirm = {
                if (isConnected) {
                    val encodedUrl = URLEncoder.encode(selectedNews.url, "UTF-8")
                    onConfirm(encodedUrl)
                } else {
                    Toast.makeText(context, "Internet is not available", Toast.LENGTH_SHORT).show()
                }
                onDismiss()
            }
        )
    }
}