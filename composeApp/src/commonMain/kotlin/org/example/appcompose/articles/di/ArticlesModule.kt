package org.example.appcompose.articles.di

import org.example.appcompose.articles.data.ArticlesDataSource
import org.example.appcompose.articles.data.ArticlesRepository
import org.example.appcompose.articles.data.ArticlesService
import org.example.appcompose.articles.data.SourceDataSource
import org.example.appcompose.articles.domain.usecase.ArticlesUseCase
import org.example.appcompose.articles.domain.usecase.ArticleByIdUseCase
import org.example.appcompose.articles.domain.usecase.SearchArticlesUseCase
import org.example.appcompose.articles.domain.usecase.SourcesUseCase
import org.example.appcompose.articles.presentation.details.ArticleViewModel
import org.example.appcompose.articles.presentation.list.ArticlesViewModel
import org.koin.dsl.module


val articlesModule = module {
    single { ArticlesService(get()) }
    single { ArticlesUseCase(get()) }
    single { ArticleByIdUseCase(get()) }
    single { SearchArticlesUseCase(get()) }
    single { SourcesUseCase(get()) }
    single { ArticlesViewModel(get(), get(), get()) }
    single { ArticleViewModel(get()) }
    single { ArticlesDataSource(get()) }
    single { SourceDataSource(get()) }
    single { ArticlesRepository(get(), get(), get()) }
}
