package com.hesham.moviedbtask.di

import com.hesham.moviedbtask.ui.movie_details.MovieDetailsViewModel
import com.hesham.moviedbtask.ui.movies_list.MoviesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesListViewModel(get()) }
    viewModel { MovieDetailsViewModel() }
}
