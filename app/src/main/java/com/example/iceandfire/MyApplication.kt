package com.example.iceandfire

import android.app.Application
import com.example.iceandfire.book.di.bookModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(bookModule)
        }
    }
}