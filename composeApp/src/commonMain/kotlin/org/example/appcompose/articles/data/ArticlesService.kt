package org.example.appcompose.articles.data

import org.example.appcompose.articles.data.model.ArticleRaw
import org.example.appcompose.articles.data.model.ArticlesResponse
import org.example.appcompose.articles.data.model.DataResponse
import org.example.appcompose.articles.data.model.SourceResponse
import org.example.appcompose.articles.data.model.SourceDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ArticlesService(private val httpClient: HttpClient) {

    suspend fun fetchArticles(sources: String): List<ArticleRaw> {
        val response: DataResponse =
            httpClient
                .get("$BASE_URL/$VERSION/top-headlines?sources=$sources&apiKey=$API_KEY")
                .body()
        return response.articles.orEmpty()
    }

    suspend fun searchArticles(query: String, category: String, sources: String): List<ArticleRaw> {
        val response: ArticlesResponse =
            httpClient
                .get("$BASE_URL/$VERSION/everything?q=$query&category=$category&sources=$sources&apiKey=$API_KEY")
                .body()
        return response.articles.orEmpty()
    }

    suspend fun loadSources(): List<SourceResponse> {
        val response: SourceDataResponse =
            httpClient
                .get("$BASE_URL/$VERSION/top-headlines/sources?&apiKey=$API_KEY")
                .body()
        return response.sources
    }

    companion object {
        private const val BASE_URL = "https://newsapi.org"
        private const val API_KEY = "63ff70d85aeb4d63b18fc08db86ea5da"
        private const val VERSION = "v2"
        private const val DEFAULT_COUNTRY = "us"
    }
}