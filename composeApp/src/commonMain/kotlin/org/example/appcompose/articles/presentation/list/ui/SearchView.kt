package org.example.appcompose.articles.presentation.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.example.appcompose.articles.presentation.list.ArticlesViewModel


@Composable
fun SearchField(
    modifier: Modifier,
    viewModel: ArticlesViewModel,
    onSearchVisible: (Boolean) -> Unit
) {
    val query = viewModel.query.collectAsState()
    val textState = remember { mutableStateOf(TextFieldValue(query.value)) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    textState.value = textState.value.copy(selection = TextRange(textState.value.text.length))
                }
            },
        value = textState.value,
        onValueChange = {
            viewModel.setQuery(it.text)
            textState.value = it
        },
        placeholder = { Text("Search") },
        leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search") },
        trailingIcon = {
            IconButton(onClick = {
                onSearchVisible(false)
                viewModel.setQuery("")
            }) {
                Icon(
                    imageVector = Icons.Outlined.Clear,
                    contentDescription = "Clear"
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        shape = RoundedCornerShape(24.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        singleLine = true
    )
}

@Composable
fun SearchProgressBar(modifier: Modifier) {
    Box(
        modifier = modifier.height(40.dp).clip(RoundedCornerShape(16.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.7f)))
        LinearProgressIndicator(
            modifier = Modifier
                .padding(horizontal = 36.dp)
                .fillMaxWidth(),
            color = Color.Black,
            strokeCap = StrokeCap.Round
        )
    }
}