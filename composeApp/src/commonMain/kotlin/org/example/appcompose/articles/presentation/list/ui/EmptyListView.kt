package org.example.appcompose.articles.presentation.list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import appcompose.composeapp.generated.resources.Res
import appcompose.composeapp.generated.resources.empty_list
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyListView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(Res.string.empty_list),
            textAlign = TextAlign.Center
        )
    }
}