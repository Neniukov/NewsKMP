package org.example.appcompose.articles.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.example.appcompose.articles.data.model.ArticleRaw

@Serializable
class DataResponse(
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: String? = null,
    @SerialName("articles")
    val articles: List<ArticleRaw>? = null,
)