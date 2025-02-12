package org.example.appcompose.articles.mapper

import org.example.appcompose.articles.data.model.ArticleRaw
import org.example.appcompose.articles.domain.model.Article
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.math.abs
import kotlin.random.Random

fun mapArticles(response: List<ArticleRaw>): List<Article> {
    return response.map {
        Article(
            id = it.id ?: -1,
            title = it.title.orEmpty(),
            desc = it.description.orEmpty(),
            date = getDaysAgoString(it.publishedAt),
            imageUrl = it.imageUrl ?: "https://picsum.photos/300/200",
            content = it.content.orEmpty(),
            source = it.source.name.orEmpty(),
            author = it.author.orEmpty(),
            url = it.url.orEmpty(),
            readTime = Random.nextInt(1, 6)
        )
    }
}


fun getDaysAgoString(date: String): String {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    try {
        val instant = Instant.parse(date)

        val days = today.daysUntil(
            if (date.contains("Z")) Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()).date
            else instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        )

        val result = when {
            abs(days) > 1 -> "${abs(days)} days ago"
            abs(days) == 1 -> "Yesterday"
            else -> "Today"
        }
        return result
    } catch (e: IllegalArgumentException) {
        return "Today"
    }

}