package org.example.appcompose.articles.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.appcompose.articles.domain.model.Article
import org.example.appcompose.articles.domain.usecase.ArticlesUseCase
import org.example.appcompose.articles.domain.usecase.SearchArticlesUseCase
import org.example.appcompose.articles.domain.usecase.SourcesUseCase

class ArticlesViewModel(
    private val articlesUseCase: ArticlesUseCase,
    private val searchArticlesUseCase: SearchArticlesUseCase,
    private val sourcesUseCase: SourcesUseCase
) : ViewModel() {

    private val _articlesState =
        MutableStateFlow<ArticlesScreenState<List<Article>>>(ArticlesScreenState(isLoading = true))
    val articlesState = _articlesState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    init {
        loadArticles()
        searchListener()
    }

    fun setQuery(query: String) {
        _query.update { query }
        if (query.isEmpty()) {
            loadArticles()
        }
    }

    fun updateData() {
        if (query.value.isNotEmpty()) return
        loadArticles(true)
    }

    fun selectUnselectSource(sourceId: String) {
        val updatedSources = articlesState.value.sources.map { source ->
            if (source.id == sourceId) {
                source.copy(selected = !source.selected)
            } else {
                source
            }
        }
        _articlesState.value = _articlesState.value.copy(sources = updatedSources)
        if (query.value.isNotEmpty()) {
            searchArticles(query.value)
        } else {
            loadArticles(forceFetch = true, isSearch = true)
        }
    }

    private fun loadArticles(forceFetch: Boolean = false, isSearch: Boolean = false) {
        viewModelScope.launch {
            if (_articlesState.value.sources.isEmpty()) {
                val sources = sourcesUseCase.getSources()
                _articlesState.emit(_articlesState.value.copy(sources = sources))
            }
            _articlesState.emit(_articlesState.value.copy(isLoading = !isSearch))

            val fetchedArticles = articlesUseCase.getArticles(forceFetch, articlesState.value.sources)
            _articlesState.emit(_articlesState.value.copy(isLoading = false, data = fetchedArticles))
        }
    }

    @OptIn(FlowPreview::class)
    private fun searchListener() {
        viewModelScope.launch {
            _query.debounce(SEARCH_DEBOUNCE).collectLatest { input ->
                searchArticles(input)
            }
        }
    }

    private fun searchArticles(query: String) {
        if (query.isEmpty()) {
            loadArticles()
            return
        }
        viewModelScope.launch {
            _articlesState.emit(
                ArticlesScreenState(
                    isSearch = true,
                    data = articlesState.value.data,
                    sources = articlesState.value.sources
                )
            )
            val searchedArticles = searchArticlesUseCase.searchArticles(query, "", articlesState.value.sources)
            _articlesState.emit(
                ArticlesScreenState(
                    data = searchedArticles,
                    sources = articlesState.value.sources
                )
            )
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 500L
    }
}