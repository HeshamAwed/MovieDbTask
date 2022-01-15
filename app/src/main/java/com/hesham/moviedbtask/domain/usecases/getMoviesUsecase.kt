package com.hesham.moviedbtask.domain.usecases

import androidx.paging.PagingData
import com.hesham.moviedbtask.domain.entities.Movie
import com.hesham.moviedbtask.domain.repos.MovieRepository
import io.reactivex.Flowable

class getMoviesUsecase(private val movieRepository: MovieRepository): (String) ->Flowable<PagingData<Movie>> {
    override fun invoke(popularity: String): Flowable<PagingData<Movie>> {
        return movieRepository.getMovieList(popularity)
    }

}