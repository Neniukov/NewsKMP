package org.example.appcompose.articles.presentation.details

import org.example.appcompose.BaseViewModel
import org.example.appcompose.articles.domain.model.Article
import org.example.appcompose.articles.domain.usecase.ArticleByIdUseCase
import org.example.appcompose.common.BaseScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticleViewModel(private val getArticleByIdUseCase: ArticleByIdUseCase) : BaseViewModel() {

    private val _articleState = MutableStateFlow<BaseScreenState<Article>>(BaseScreenState(isLoading = true))
    val articleState = _articleState.asStateFlow()

    fun getArticleById(id: String) {
        val result = getArticleByIdUseCase(id.toLong())
        _articleState.value = BaseScreenState(data = result)
    }
}