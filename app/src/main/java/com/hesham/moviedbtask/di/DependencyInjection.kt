package com.hesham.moviedbtask.di

import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.hesham.moviedbtask.domain.datasources.MovieDataSource
import com.hesham.moviedbtask.domain.datasources.MoviesRemoteMediator
import com.hesham.moviedbtask.domain.gateways.local.db.AppDatabase
import com.hesham.moviedbtask.domain.gateways.remote.ApiService
import com.hesham.moviedbtask.domain.gateways.remote.retrofitBuilder
import com.hesham.moviedbtask.domain.repos.MovieRepository
import com.hesham.moviedbtask.domain.repos.MovieRepositoryImpl
import com.hesham.moviedbtask.domain.usecases.getMoviesUsecase
import com.hesham.moviedbtask.ui.Constants
import com.hesham.moviedbtask.ui.movie_details.MovieDetailsViewModel
import com.hesham.moviedbtask.ui.movies_list.MoviesListViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val applicationModule = module {
    factory { CompositeDisposable() }
    single<RxSchedulers> {
        RxSchedulersImpl()
    }
 }
val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single { get<AppDatabase>().movieDAO() }
    single { get<AppDatabase>().movieRemoteKeys() }
}

val networkModule = module {
    single {
        retrofitBuilder().create(ApiService::class.java) as ApiService
    }
}

@OptIn(ExperimentalPagingApi::class)
val dataSourcesModule = module {
   // single{MovieDataSource(get(),get(), Constants.POPULAR_MOVIE) }
    single{ MoviesRemoteMediator(get(),get(),get(),get()) }
}

val repositoriesModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(get(),get())
    }
}

val usecasesModule = module {
    single {
        getMoviesUsecase(get())
    }
}


val viewModelModule = module {
    viewModel { MoviesListViewModel(get()) }
    viewModel { MovieDetailsViewModel() }
}


val applicationModules = listOf(
    applicationModule,
    databaseModule,
    networkModule,
    dataSourcesModule,
    repositoriesModule,
    usecasesModule,
    viewModelModule
)