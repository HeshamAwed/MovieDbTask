package com.hesham.moviedbtask

import android.app.Application
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.hesham.moviedbtask.di.*
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
    @ExperimentalPagingApi
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