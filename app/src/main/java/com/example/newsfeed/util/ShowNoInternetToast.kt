package com.example.newsfeed.util

import android.content.Context
import android.widget.Toast

fun showNoInternetToast(context: Context) {
    Toast.makeText(context, "Internet is not available", Toast.LENGTH_SHORT).show()
}