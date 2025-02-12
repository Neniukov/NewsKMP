package org.example.appcompose.articles.presentation.list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appcompose.composeapp.generated.resources.Res
import appcompose.composeapp.generated.resources.ic_horizontal_list
import appcompose.composeapp.generated.resources.ic_news
import appcompose.composeapp.generated.resources.ic_vertical_list
import kotlinx.coroutines.delay
import org.example.appcompose.articles.presentation.list.ArticlesViewModel
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.MaterialTheme
import appcompose.composeapp.generated.resources.all_news
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppBar(
    viewModel: ArticlesViewModel,
    isHorizontalList: Boolean,
    onChangeLayout: () -> Unit,
    onShowSources: () -> Unit
) {
    var isSearchBarVisible by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedVisibility(
            visible = !isSearchBarVisible,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Toolbar(
                isHorizontalList,
                onChangeLayout,
                { isSearchBarVisible = it },
                onShowSources
            )
        }

        LaunchedEffect(isSearchBarVisible) {
            delay(Spring.StiffnessMediumLow.toLong())
            if (isSearchBarVisible) {
                focusRequester.requestFocus()
            }
        }

        AnimatedVisibility(
            visible = isSearchBarVisible,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            SearchField(Modifier.focusRequester(focusRequester), viewModel) {
                isSearchBarVisible = it
            }
        }
    }
}

@Composable
private fun Toolbar(
    isHorizontalList: Boolean,
    onChangeLayout: () -> Unit,
    onSearchVisible: (Boolean) -> Unit,
    onShowSources: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onShowSources
                ),
            painter = painterResource(Res.drawable.ic_news),
            contentDescription = "news",
            tint = MaterialTheme.colorScheme.secondary
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onShowSources
                ),
            text = stringResource(Res.string.all_news),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
        )

        IconButton(onClick = { onSearchVisible(true) }) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search"
            )
        }
        IconButton(onClick = onChangeLayout) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(if (isHorizontalList) Res.drawable.ic_vertical_list else Res.drawable.ic_horizontal_list),
                contentDescription = "Change layout"
            )
        }
    }
}