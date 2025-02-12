package org.example.appcompose.articles.presentation.list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch
import org.example.appcompose.articles.domain.model.Article
import org.example.appcompose.articles.presentation.list.ArticlesScreenState
import org.example.appcompose.common.ui.NetworkImage
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesListView(
    modifier: Modifier = Modifier,
    screenState: ArticlesScreenState<List<Article>>,
    isHorizontalList: Boolean,
    onArticlesDetailsClick: (String) -> Unit,
    onScrollArticles: () -> Unit,
    onRefreshData: () -> Unit,
) {

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = screenState.isLoading,
        onRefresh = { onRefreshData() }
    ) {
        val animationSpec = tween<Float>(durationMillis = 300, easing = FastOutLinearInEasing)
        val enterAnimation = scaleIn(animationSpec) + fadeIn(animationSpec)
        val exitAnimation = scaleOut(animationSpec) + fadeOut(animationSpec)
        var currentPosition by rememberSaveable { mutableIntStateOf(0) }

        val pagerState =
            rememberPagerState(pageCount = { screenState.data.orEmpty().size })
        val listState = rememberLazyListState()

        val scope = rememberCoroutineScope()

        LaunchedEffect(isHorizontalList) {
            currentPosition = if (isHorizontalList) {
                pagerState.currentPage
            } else {
                listState.firstVisibleItemIndex
            }
        }

        LaunchedEffect(pagerState.isScrollInProgress) {
            if (pagerState.isScrollInProgress) onScrollArticles()
        }

        LaunchedEffect(listState.isScrollInProgress) {
            if (listState.isScrollInProgress) onScrollArticles()
        }


        AnimatedVisibility(
            visible = isHorizontalList,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            HorizontalNewsList(
                modifier = Modifier.fillMaxSize(),
                articles = screenState.data.orEmpty(),
                pagerState = pagerState,
                onArticlesDetailsClick = {
                    currentPosition = pagerState.currentPage
                    onArticlesDetailsClick(it)
                    scope.launch {
                        listState.scrollToItem(currentPosition)
                    }
                }
            )

            LaunchedEffect(currentPosition) {
                pagerState.scrollToPage(currentPosition)
            }
        }

        AnimatedVisibility(
            visible = !isHorizontalList,
            enter = enterAnimation,
            exit = exitAnimation
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = screenState.data.orEmpty(),
                    key = { it.id }
                ) { article ->
                    ArticleItemView(
                        article = article,
                        onArticlesDetailsClick = {
                            currentPosition = listState.firstVisibleItemIndex
                            onArticlesDetailsClick(it)
                            scope.launch {
                                pagerState.scrollToPage(currentPosition)
                            }
                        }
                    )
                }
            }

            LaunchedEffect(currentPosition) {
                listState.scrollToItem(currentPosition)
            }

        }
    }
}

@Composable
private fun HorizontalNewsList(
    modifier: Modifier,
    articles: List<Article>,
    pagerState: PagerState,
    onArticlesDetailsClick: (String) -> Unit
) {

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 36.dp),
        pageSpacing = 16.dp,
        key = { index -> articles[index].id }
    ) { page ->
        Card(
            modifier = Modifier.graphicsLayer {
                val pageOffset =
                    ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                val transformationAlpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
                val transformationY = lerp(
                    start = 0.95f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
                alpha = transformationAlpha
                scaleY = transformationY
            },
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors().copy(containerColor = Color.Transparent),
        ) {
            HorizontalArticleItemView(articles[page], onArticlesDetailsClick)
        }
    }
}

@Composable
private fun ArticleItemView(article: Article, onArticlesDetailsClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 16.dp)
            .clickable { onArticlesDetailsClick(article.id.toString()) }
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(20.dp))
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(0.7f)
        ) {
            Text(
                modifier = Modifier.weight(0.9f),
                text = article.title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${article.source} • ${article.date}",
                style = TextStyle(color = Color.Gray)
            )
        }

        NetworkImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp)),
            url = article.imageUrl,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun HorizontalArticleItemView(article: Article, onArticlesDetailsClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onArticlesDetailsClick(article.id.toString()) },
        verticalArrangement = Arrangement.Center
    ) {
        NetworkImage(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .clip(RoundedCornerShape(16.dp)),
            url = article.imageUrl,
            contentScale = ContentScale.FillHeight
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = article.title,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = article.desc,
            style = TextStyle(lineHeight = 26.sp, fontSize = 16.sp),
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.padding(vertical = 24.dp),
            text = "${article.source} • ${article.date}",
            style = TextStyle(color = Color.Gray)
        )
    }
}