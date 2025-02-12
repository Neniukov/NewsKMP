package org.example.appcompose.articles.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.appcompose.articles.data.model.ArticleRaw

@Serializable
data class ArticlesResponse(
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int?,
    @SerialName("articles")
    val articles: List<ArticleRaw>?
)