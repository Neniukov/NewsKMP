package org.example.appcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.example.appcompose.utils.ShareHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shareHelper = ShareHelper(this)
        setContent {
            AppScreen(shareHelper)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
//    AppScreen()
}