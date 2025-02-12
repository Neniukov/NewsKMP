package org.example.appcompose.articles.data

import org.example.appcompose.articles.data.model.SourceResponse
import com.neniukov.alanyaweather.db.Sources
import org.example.appcompose.db.ArticlesDatabase

class SourceDataSource(private val database: ArticlesDatabase) {

    fun getSources() = database.articlesDatabaseQueries.selectAllSources(::mapSource).executeAsList()

    fun insertSources(sources: List<SourceResponse>) {
        database.articlesDatabaseQueries.removeAllSources()
        database.articlesDatabaseQueries.transaction {
            sources.forEach { source ->
                insertSource(source)
            }
        }
    }

    private fun mapSource(id: String?, name: String?) = Sources(id, name)

    private fun insertSource(source: SourceResponse) {
        if (source.id == null || source.name == null) return
        database.articlesDatabaseQueries.inseartSource(source.id, source.name)
    }
}