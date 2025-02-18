package org.example.appcompose.articles.presentation.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.appcompose.articles.domain.model.Article
import org.example.appcompose.articles.domain.usecase.ArticleByIdUseCase
import org.example.appcompose.common.BaseScreenState

class ArticleViewModel(private val getArticleByIdUseCase: ArticleByIdUseCase) : ViewModel() {

    private val _articleState = MutableStateFlow<BaseScreenState<Article>>(BaseScreenState(isLoading = true))
    val articleState = _articleState.asStateFlow()

    fun getArticleById(id: String) {
        val result = getArticleByIdUseCase(id.toLong())
        _articleState.value = BaseScreenState(data = result)
    }
}