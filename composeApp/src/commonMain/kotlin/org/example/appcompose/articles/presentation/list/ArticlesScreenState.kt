package org.example.appcompose.articles.presentation.list

import org.example.appcompose.articles.domain.model.Source
import org.example.appcompose.common.BaseScreenStateI

data class ArticlesScreenState<T>(
    override val data: T? = null,
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isSearch: Boolean = false,
    val sources: List<Source> = emptyList()
) : BaseScreenStateI<T>