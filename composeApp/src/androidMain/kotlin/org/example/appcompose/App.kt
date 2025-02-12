package org.example.appcompose

import android.app.Application
import org.example.appcompose.di.viewModelsModule
import org.example.appcompose.di.databaseModule
import org.example.appcompose.di.sharedKotlinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.plus

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = sharedKotlinModules + viewModelsModule + databaseModule
        startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }
}