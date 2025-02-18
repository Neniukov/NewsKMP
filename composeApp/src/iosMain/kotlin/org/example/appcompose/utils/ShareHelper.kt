package org.example.appcompose.utils

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual class ShareHelper {
    actual fun shareLink(url: String) {
        val controller = UIActivityViewController(listOf(url), null)
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootViewController?.presentViewController(controller, animated = true, completion = null)
    }
}