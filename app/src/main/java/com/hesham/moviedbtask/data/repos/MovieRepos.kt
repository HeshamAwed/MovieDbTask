package com.hesham.moviedbtask.data.repos

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.hesham.moviedbtask.data.Constants
import com.hesham.moviedbtask.data.local.dao.MovieDAO
import com.hesham.moviedbtask.data.model.MovieModel
import com.hesham.moviedbtask.data.remote.ApiService
import com.hesham.moviedbtask.util.isOnline

class MovieRepos(var movieDAO: MovieDAO, var apiService: ApiService) {

    fun getMovieDataSource(popularity: String): PagingSource<Int, MovieModel> {
        return if (isOnline())
            MovieDataSource(apiService, movieDAO, popularity)
        else
            movieDAO.getMovieList(popularity)
    }

    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = Constants.DEFAULT_ITEM_PER_PAGE, enablePlaceholders = false)
    }
}
