package org.example.appcompose.di

import org.example.appcompose.articles.presentation.details.ArticleViewModel
import org.example.appcompose.articles.presentation.list.ArticlesViewModel
import org.koin.core.module.dsl.*
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ArticlesViewModel(get(), get(), get()) }
    viewModel { ArticleViewModel(get()) }
}