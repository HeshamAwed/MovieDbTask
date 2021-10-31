package com.hesham.moviedbtask.di

import com.hesham.moviedbtask.data.repos.MovieRepos
import org.koin.dsl.module

val repositoriesModule = module {

    factory { MovieRepos(get(), get()) }
}
