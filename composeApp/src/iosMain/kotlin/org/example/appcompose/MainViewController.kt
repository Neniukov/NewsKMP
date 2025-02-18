package org.example.appcompose

import androidx.compose.ui.window.ComposeUIViewController
import org.example.appcompose.utils.ShareHelper
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val shareHelper = ShareHelper()

    return ComposeUIViewController {
        AppScreen(shareHelper)
    }
}