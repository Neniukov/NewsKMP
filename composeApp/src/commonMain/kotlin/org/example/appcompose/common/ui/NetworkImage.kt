package org.example.appcompose.common.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.rememberImagePainter

@Composable
fun NetworkImage(url: String, modifier: Modifier = Modifier, contentScale: ContentScale = ContentScale.Fit) {
    val painter = rememberImagePainter(url)

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}