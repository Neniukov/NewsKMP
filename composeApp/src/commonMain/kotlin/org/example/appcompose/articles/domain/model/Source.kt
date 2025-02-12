package org.example.appcompose.articles.domain.model

data class Source(
    val id: String,
    val name: String,
    val selected: Boolean = true
)