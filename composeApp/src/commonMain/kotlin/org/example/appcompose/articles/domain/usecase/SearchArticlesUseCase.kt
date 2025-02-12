package org.example.appcompose.articles.domain.usecase

import org.example.appcompose.articles.data.ArticlesRepository
import org.example.appcompose.articles.domain.model.Article
import org.example.appcompose.articles.domain.model.Source
import org.example.appcompose.articles.mapper.mapArticles

class SearchArticlesUseCase(private val articlesRepository: ArticlesRepository) {

    suspend fun searchArticles(query: String, category: String, sources: List<Source>): List<Article> {
        return mapArticles(
            articlesRepository.searchArticles(
                query,
                category,
                sources.filter { it.selected }.joinToString { it.id })
        )
    }
}