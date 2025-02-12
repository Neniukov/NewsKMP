package org.example.appcompose.articles.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleRaw(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("title")
    val title: String? = "",
    @SerialName("description")
    val description: String?,
    @SerialName("publishedAt")
    val publishedAt: String,
    @SerialName("urlToImage")
    val imageUrl: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("source")
    val source: SourceResponse,
    @SerialName("author")
    val author: String?,
    @SerialName("url")
    val url: String?,
    val isSearch: Boolean = false
)
