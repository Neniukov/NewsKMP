package org.example.appcompose.articles.domain.model

data class Article(
    val id: Long,
    val title: String,
    val desc: String,
    val date: String,
    val imageUrl: String,
    val content: String,
    val source: String,
    val author: String,
    val url: String?,
    val readTime: Int = 5,
)