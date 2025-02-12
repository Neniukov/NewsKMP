package org.example.appcompose.common.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import appcompose.composeapp.generated.resources.Res
import appcompose.composeapp.generated.resources.ic_arrow_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    tintColor: Color = Color.White,
    onBackClick: () -> Unit
) {
    IconButton(
        modifier = modifier.size(36.dp),
        onClick = onBackClick
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(Res.drawable.ic_arrow_back),
            contentDescription = "Back",
            tint = tintColor
        )
    }
}