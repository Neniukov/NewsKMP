package org.example.appcompose.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route: String) {

    @Serializable
    data object Articles : Screen("articles")

    @Serializable
    data class ArticleDetails(val articleId: String) : Screen("article_details/$articleId")

    data object About : Screen("about")
}