package org.example.appcompose

import androidx.compose.runtime.Composable
import org.example.appcompose.theme.AppTheme
import org.example.appcompose.utils.ShareHelper
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun AppScreen(shareHelper: ShareHelper) {
    AppTheme {
        AppScaffold(shareHelper)
    }
}