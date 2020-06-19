package com.nanoyatsu.example.clean.poke.presentation

import android.app.Application
import com.nanoyatsu.example.clean.poke.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokeApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PokeApplication)
            androidLogger()
            modules(appModule)
        }
    }
}