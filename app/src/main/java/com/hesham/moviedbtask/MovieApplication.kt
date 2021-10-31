package com.hesham.moviedbtask

import android.app.Application
import android.content.Context
import com.hesham.moviedbtask.di.databaseModule
import com.hesham.moviedbtask.di.networkModule
import com.hesham.moviedbtask.di.repositoriesModule
import com.hesham.moviedbtask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    companion object {
        lateinit var context: Context
        fun getAppContext(): Context {
            return context
        }
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@MovieApplication)
            modules(listOf(viewModelModule, networkModule, repositoriesModule, databaseModule))
        }
    }
}
