package org.example.appcompose.di

import org.example.appcompose.db.ArticlesDatabase
import org.example.appcompose.db.DatabaseDriverFactory
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseDriverFactory().createDriver() }
    single { ArticlesDatabase(get()) }
}