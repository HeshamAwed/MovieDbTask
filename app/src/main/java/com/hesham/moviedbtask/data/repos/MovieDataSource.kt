package com.hesham.moviedbtask.data.repos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hesham.moviedbtask.data.Constants.DEFAULT_FIRST_PAGE
import com.hesham.moviedbtask.data.local.dao.MovieDAO
import com.hesham.moviedbtask.data.model.MovieModel
import com.hesham.moviedbtask.data.remote.ApiService

class MovieDataSource(val apiService: ApiService,val movieDAO: MovieDAO,val popularity:String) : PagingSource<Int,MovieModel>(){
    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val page = params.key ?: DEFAULT_FIRST_PAGE
           val response =  apiService.getListOfMovies(popularity,page)
            response.results?.let {
                it.forEach { it.filter_type = popularity }
                movieDAO.insertAll(it)
            }
            LoadResult.Page(
                data = response.results?: listOf(),
                prevKey = if (page == DEFAULT_FIRST_PAGE) null else page - 1,
                nextKey = page + 1
            )
        } catch (exception: Throwable) {
            return LoadResult.Error(exception)
        }
    }



}