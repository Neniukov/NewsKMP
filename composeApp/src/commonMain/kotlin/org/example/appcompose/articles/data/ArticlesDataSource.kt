package org.example.appcompose.articles.data

import org.example.appcompose.articles.data.model.ArticleRaw
import org.example.appcompose.articles.data.model.SourceResponse
import org.example.appcompose.db.ArticlesDatabase

class ArticlesDataSource(private val database: ArticlesDatabase) {

    fun getArticles() = database.articlesDatabaseQueries.selectAllArticles(::mapToArticleRaw).executeAsList()

    fun getSearchedArticles() = database.articlesDatabaseQueries.selectSearchedArticles(::mapToArticleRaw).executeAsList()

    fun insertArticles(articles: List<ArticleRaw>, isSearch: Boolean = false) {
        database.articlesDatabaseQueries.transaction {
            articles.forEach { article ->
                insertArticle(article, isSearch)
            }
        }
    }

    fun getArticlesById(id: Long): ArticleRaw {
        println("article Id : $id")
        return database.articlesDatabaseQueries
            .selectArticleById(id, ::mapToArticleRaw)
            .executeAsOne()
    }

    fun clearArticles() = database.articlesDatabaseQueries.removeAllArticles()

    fun clearSearchedArticles() = database.articlesDatabaseQueries.removeSearchedArticles()

    private fun insertArticle(article: ArticleRaw, isSearch: Boolean) {
        database.articlesDatabaseQueries.insertArticle(
            null,
            article.title.orEmpty(),
            article.description,
            article.publishedAt,
            article.imageUrl,
            article.content,
            article.source.name,
            article.author,
            article.url,
            if (isSearch) 1 else 0
        )
    }

    private fun mapToArticleRaw(
        id: Long?,
        title: String,
        description: String?,
        date: String,
        imageUrl: String?,
        content: String?,
        source: String?,
        author: String?,
        url: String?,
        isSearch: Long?
    ) = ArticleRaw(id, title, description, date, imageUrl, content, SourceResponse(source, source), author, url, isSearch == 1L)
}