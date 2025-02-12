package org.example.appcompose.articles.data

import org.example.appcompose.articles.data.model.ArticleRaw
import com.neniukov.alanyaweather.db.Sources

class ArticlesRepository(
    private val articlesDataSource: ArticlesDataSource,
    private val sourceDataSource: SourceDataSource,
    private val service: ArticlesService
) {

    suspend fun getArticles(forceFetch: Boolean, sources: String): List<ArticleRaw> {
        articlesDataSource.clearSearchedArticles()
        if (forceFetch) {
            articlesDataSource.clearArticles()
            return fetchArticles(sources)
        }
        val articles = articlesDataSource.getArticles()
        if (articles.isEmpty()) {
            return fetchArticles(sources)
        }
        return articles.filter { it.isSearch.not() }
    }

    suspend fun getSources(): List<Sources> {
        val sources = sourceDataSource.getSources()
        if (sources.isEmpty()) {
            return fetchSources()
        }
        return sources
    }

    fun getArticleById(id: Long): ArticleRaw {
        return articlesDataSource.getArticlesById(id)
    }

    suspend fun searchArticles(query: String, category: String, sources: String): List<ArticleRaw> {
        val articles = service.searchArticles(query, category, sources)
        articlesDataSource.insertArticles(articles, isSearch = true)
        return articlesDataSource.getSearchedArticles()
    }

    private suspend fun fetchArticles(sources: String): List<ArticleRaw> {
        val newArticles = service.fetchArticles(sources)
        articlesDataSource.insertArticles(newArticles)
        return articlesDataSource.getArticles()
    }

    private suspend fun fetchSources(): List<Sources> {
        val sources = service.loadSources()
        sourceDataSource.insertSources(sources)
        return sourceDataSource.getSources()
    }
}