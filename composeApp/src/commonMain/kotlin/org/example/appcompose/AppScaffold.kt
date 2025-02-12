package org.example.appcompose

import androidx.compose.runtime.Composable
import org.example.appcompose.articles.presentation.list.ui.ArticlesScreen

@Composable
fun AppScaffold() {
//    Scaffold { it ->
        ArticlesScreen(
            onAboutButtonClick = { },
            onArticlesDetailsClick = { articleId ->

            }
        )
//    }
}