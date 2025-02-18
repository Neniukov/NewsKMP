package org.example.appcompose.utils

import android.content.Context
import android.content.Intent

actual class ShareHelper(private val context: Context) {
    actual fun shareLink(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        context.startActivity(intent)
    }
}