package org.example.appcompose.di

import org.example.appcompose.articles.di.articlesModule
import org.example.appcompose.di.networkModule

val sharedKotlinModules = listOf(
    networkModule,
    articlesModule
)