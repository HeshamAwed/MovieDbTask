package com.hesham.moviedbtask.di

import com.hesham.moviedbtask.data.remote.ApiService
import com.hesham.moviedbtask.data.remote.retrofitBuilder
import org.koin.dsl.module

val networkModule = module {
    single {
        retrofitBuilder().create(ApiService::class.java) as ApiService
    }
}
