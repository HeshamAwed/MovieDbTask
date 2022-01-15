package com.hesham.moviedbtask.domain.repos

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.hesham.moviedbtask.domain.datasources.MoviesRemoteMediator
import com.hesham.moviedbtask.domain.entities.Movie
import com.hesham.moviedbtask.domain.gateways.local.dao.MovieDAO
import io.reactivex.Flowable

interface MovieRepository {
    fun getMovieList(popularity: String): Flowable<PagingData<Movie>>
}

class MovieRepositoryImpl @OptIn(ExperimentalPagingApi::class) constructor(
    private val moviesRemoteMediator: MoviesRemoteMediator,
    private val movieDAO: MovieDAO
) : MovieRepository {
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 40
        )
    }


    @ExperimentalPagingApi
    override fun getMovieList(popularity: String): Flowable<PagingData<Movie>> {
        moviesRemoteMediator.setPopularity(popularity)
        return Pager(
            config = getDefaultPageConfig(),
            remoteMediator = moviesRemoteMediator,
            pagingSourceFactory = { movieDAO.getMovieList(popularity) }
        ).flowable
    }


}
