package org.example.appcompose.articles.presentation.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import appcompose.composeapp.generated.resources.Res
import appcompose.composeapp.generated.resources.author_avatar
import appcompose.composeapp.generated.resources.dots
import appcompose.composeapp.generated.resources.ic_favorite
import appcompose.composeapp.generated.resources.ic_share
import appcompose.composeapp.generated.resources.ic_time
import appcompose.composeapp.generated.resources.more
import appcompose.composeapp.generated.resources.posted
import appcompose.composeapp.generated.resources.read_time
import org.example.appcompose.articles.domain.model.Article
import org.example.appcompose.common.BaseScreenState
import org.example.appcompose.common.ui.BackButton
import org.example.appcompose.common.ui.ErrorMessage
import org.example.appcompose.common.ui.Loader
import org.example.appcompose.common.ui.NetworkImage
import org.example.appcompose.utils.ShareHelper
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ArticleDetailsScreen(
    articleId: String,
    shareHelper: ShareHelper,
    onBackClick: () -> Unit
) {
    val viewModel = koinViewModel<ArticleViewModel>()

    val screenState = viewModel.articleState.collectAsStateWithLifecycle()

    val overlapHeightPx = with(LocalDensity.current) {
        EXPANDED_TOP_BAR_HEIGHT.toPx() - COLLAPSED_TOP_BAR_HEIGHT.toPx()
    }

    val listState = rememberLazyListState()

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden = listState.firstVisibleItemScrollOffset > overlapHeightPx
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }

    when {
        screenState.value.isLoading -> Loader()
        screenState.value.error != null -> ErrorMessage(screenState.value.error!!)
        screenState.value.data != null -> {
            ArticleContent(screenState, isCollapsed, onBackClick, listState, shareHelper)
        }
    }

    LaunchedEffect(articleId) {
        viewModel.getArticleById(articleId)
    }
}

@Composable
private fun ArticleContent(
    screenState: State<BaseScreenState<Article>>,
    isCollapsed: Boolean,
    onBackClick: () -> Unit,
    listState: LazyListState,
    shareHelper: ShareHelper
) {
    val article = screenState.value.data!!
    Box {
        CollapsedTopBar(
            modifier = Modifier.zIndex(2f),
            isCollapsed = isCollapsed,
            onBackClick = onBackClick
        )
        LazyColumn(state = listState) {
            item {
                ExpandedTopBar(
                    article.imageUrl,
                    { article.url?.let(shareHelper::shareLink) },
                    onBackClick,
                    onFavoriteClick = {})
            }
            item {
                Text(
                    text = article.title,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                AuthorView(article)
            }
            item {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    color = Color.Gray
                )
            }

            item {
                DescriptionView(article)
            }
        }
    }
}

@Composable
private fun DescriptionView(article: Article) {
    val moreText = stringResource(Res.string.more)
    val annotatedText = buildAnnotatedString {
        append(article.desc)
        if (article.content.isNotEmpty()) {
            append(" ")
            append(article.content)
        }
        append(stringResource(Res.string.dots))

        val start = length

        withStyle(
            MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        ) {
            append(moreText)
        }

        addStringAnnotation(
            tag = ARTICLE_URL_TAG,
            annotation = article.url.orEmpty(),
            start = start,
            end = start + moreText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = ARTICLE_URL_TAG, start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )
}

@Composable
private fun CollapsedTopBar(modifier: Modifier = Modifier, isCollapsed: Boolean, onBackClick: () -> Unit) {
    val color: Color by animateColorAsState(
        if (isCollapsed) MaterialTheme.colorScheme.background else Color.Transparent, label = ""
    )
    Box(
        modifier = modifier
            .background(color)
            .fillMaxWidth()
            .height(COLLAPSED_TOP_BAR_HEIGHT)
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AnimatedVisibility(visible = isCollapsed) {
            BackButton(
                modifier = Modifier.padding(top = 32.dp),
                tintColor = MaterialTheme.colorScheme.primary,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
private fun ExpandedTopBar(
    imageUrl: String,
    onShareClick: () -> Unit,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(EXPANDED_TOP_BAR_HEIGHT)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
    ) {
        NetworkImage(
            modifier = Modifier.fillMaxSize(),
            url = imageUrl,
            contentScale = ContentScale.Crop,
        )
        BackButton(
            modifier = Modifier.statusBarsPadding().padding(start = 16.dp, top = 16.dp),
            onBackClick = onBackClick
        )
        val brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f)))
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .align(Alignment.BottomCenter),
            onDraw = {
                drawRect(brush = brush)
            }
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(50)
                )
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_favorite),
                    contentDescription = "favorite",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }

            IconButton(
                onClick = onShareClick,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(50)
                )
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_share),
                    contentDescription = "share",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Composable
private fun AuthorView(article: Article) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(Res.drawable.author_avatar),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = article.author.ifEmpty { article.source },
                style = TextStyle(fontWeight = FontWeight.Medium, fontSize = 16.sp)
            )
            Text(
                text = "${stringResource(Res.string.posted)} ${article.date}",
                style = TextStyle(fontWeight = FontWeight.Light, fontSize = 12.sp, color = Color.Gray)
            )
        }
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(Res.drawable.ic_time),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "${article.readTime} ${stringResource(Res.string.read_time)}",
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error
            )
        )
    }
}

private val COLLAPSED_TOP_BAR_HEIGHT = 100.dp
private val EXPANDED_TOP_BAR_HEIGHT = 300.dp
private const val ARTICLE_URL_TAG = "ARTICLE_URL_TAG"