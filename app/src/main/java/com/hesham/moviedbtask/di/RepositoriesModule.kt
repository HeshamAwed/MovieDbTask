package com.hesham.moviedbtask.di

import androidx.paging.ExperimentalPagingApi
import com.hesham.moviedbtask.data.repos.MovieRepos
import org.koin.dsl.module

@ExperimentalPagingApi
val repositoriesModule = module {

    factory { MovieRepos(get(),get())}
}