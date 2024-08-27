package com.example.newsfeed.presentation.details

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class CustomWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : WebView(context, attrs) {

    var onScrollChangedListener: ((Int) -> Unit)? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        onScrollChangedListener?.invoke(t)
    }
}