package org.example.appcompose.di

import org.koin.core.context.startKoin

class KoinIos {
    fun initKoin() {
        val modules = sharedKotlinModules + databaseModule
        startKoin {
            modules(modules)
        }
    }
}