package org.example.appcompose.di

import org.example.appcompose.db.ArticlesDatabase
import org.example.appcompose.db.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { DatabaseDriverFactory(androidContext()).createDriver() }
    single { ArticlesDatabase(get()) }
}