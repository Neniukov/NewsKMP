package org.example.appcompose

import androidx.compose.runtime.Composable
import org.example.appcompose.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun AppScreen() {
    AppTheme {
        AppScaffold()
    }
}