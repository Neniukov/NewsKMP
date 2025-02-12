package org.example.appcompose.articles.domain.usecase

import org.example.appcompose.articles.data.ArticlesRepository
import org.example.appcompose.articles.domain.model.Article
import org.example.appcompose.articles.domain.model.Source
import org.example.appcompose.articles.mapper.mapArticles

class ArticlesUseCase(private val repository: ArticlesRepository) {

    suspend fun getArticles(forceFetch: Boolean, sources: List<Source>): List<Article> {
        val response = repository.getArticles(
            forceFetch,
            sources.filter { it.selected }.joinToString { it.id }
        )
        return mapArticles(response)
    }
}