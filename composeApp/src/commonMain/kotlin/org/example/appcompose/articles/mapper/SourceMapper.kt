package org.example.appcompose.articles.mapper

import org.example.appcompose.articles.domain.model.Source
import com.neniukov.alanyaweather.db.Sources

fun mapSource(sources: Sources): Source {
    return Source(
        id = sources.id ?: "",
        name = sources.name ?: ""
    )
}