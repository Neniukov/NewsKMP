package org.example.appcompose.articles.domain.usecase

import org.example.appcompose.articles.data.ArticlesRepository
import org.example.appcompose.articles.domain.model.Source
import org.example.appcompose.articles.mapper.mapSource

class SourcesUseCase(private val repository: ArticlesRepository) {

    suspend fun getSources(): List<Source> {
        return repository.getSources().map { mapSource(it) }
    }
}