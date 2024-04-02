package com.example.newsfeed

import android.util.Log

interface Logger {
    fun e(tag: String, message: String)
}

fun LogCatLogger() = object : Logger{

    override fun e(tag: String, message: String) {
       Log.e(tag, message)
    }
}