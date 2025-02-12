package org.example.appcompose.articles.domain.usecase

import org.example.appcompose.articles.data.ArticlesRepository
import org.example.appcompose.articles.domain.model.Article
import org.example.appcompose.articles.mapper.getDaysAgoString

class ArticleByIdUseCase(private val repository: ArticlesRepository) {

    operator fun invoke(id: Long): Article {
        return repository.getArticleById(id).let {
            Article(
                id = it.id ?: -1,
                title = it.title.orEmpty(),
                desc = it.description ?: "Click to find out more!",
                date = getDaysAgoString(it.publishedAt),
                imageUrl = it.imageUrl ?: "https://picsum.photos/300/200",
                content = it.content ?: "",
                author = it.author.orEmpty(),
                source = it.source.name.orEmpty(),
                url = it.url.orEmpty()
            )
        }
    }

}