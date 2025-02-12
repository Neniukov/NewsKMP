package org.example.appcompose.articles.presentation.list.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.example.appcompose.articles.presentation.list.ArticlesViewModel
import org.example.appcompose.common.ui.ErrorMessage
import org.example.appcompose.common.ui.Loader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ArticlesScreen(
    onAboutButtonClick: () -> Unit,
    onArticlesDetailsClick: (String) -> Unit
) {
    val viewModel: ArticlesViewModel = koinViewModel()
    val articlesState = viewModel.articlesState.collectAsState()
    var isHorizontalList by rememberSaveable { mutableStateOf(false) }
    var isSourcesVisible by remember { mutableStateOf(false) }
    val sourcesHeight by animateDpAsState(if (isSourcesVisible) 48.dp else 0.dp, label = "sourcesHeight")

    Column(modifier = Modifier.statusBarsPadding()) {
        AppBar(
            viewModel = viewModel,
            isHorizontalList = isHorizontalList,
            onChangeLayout = {
                isHorizontalList = !isHorizontalList
            },
            onShowSources = {
                isSourcesVisible = !isSourcesVisible
            }
        )
        Box {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.height(44.dp),
                visible = isSourcesVisible,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                SourcesList(articlesState.value.sources, viewModel::selectUnselectSource)
            }

            when {
                articlesState.value.isLoading && articlesState.value.data.isNullOrEmpty() -> Loader()
                articlesState.value.error != null -> ErrorMessage(articlesState.value.error!!)
                articlesState.value.data!!.isNotEmpty() -> {
                    ArticlesListView(
                        modifier = Modifier
                            .offset { IntOffset(0, sourcesHeight.roundToPx()) }
                            .padding(top = 4.dp),
                        screenState = articlesState.value,
                        isHorizontalList = isHorizontalList,
                        onArticlesDetailsClick = onArticlesDetailsClick,
                        onScrollArticles = { isSourcesVisible = false },
                        onRefreshData = viewModel::updateData
                    )
                }

                articlesState.value.data != null && articlesState.value.data!!.isEmpty() -> EmptyListView()
            }

            if (articlesState.value.isSearch) {
                SearchProgressBar(Modifier.align(Alignment.Center))
            }
        }

    }
}