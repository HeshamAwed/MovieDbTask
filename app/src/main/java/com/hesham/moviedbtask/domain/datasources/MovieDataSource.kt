package com.hesham.moviedbtask.domain.datasources

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.hesham.moviedbtask.di.RxSchedulers
import com.hesham.moviedbtask.domain.entities.Movie
import com.hesham.moviedbtask.domain.entities.MovieList
import com.hesham.moviedbtask.domain.gateways.local.dao.MovieDAO
import com.hesham.moviedbtask.domain.gateways.remote.ApiService
import io.reactivex.Single


//class MovieDataSource(val apiService: ApiService, val movieDAO: MovieDAO, val popularity: String) : PagingSource<Int, Movie>() {
//    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
//
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
//        return try {
//            val page = params.key ?: DEFAULT_FIRST_PAGE
//            val response = apiService.getListOfMovies(popularity, page)
//            response.results?.let {
//                it.forEach { it.filter_type = popularity }
//                movieDAO.insertAll(it)
//            }
//            LoadResult.Page(
//                data = response.results ?: listOf(),
//                prevKey = if (page == DEFAULT_FIRST_PAGE) null else page - 1,
//                nextKey = page + 1
//            )
//        } catch (exception: Throwable) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//}
//
//

class MovieDataSource(
    private val schedulers: RxSchedulers,
    private val apiService: ApiService,
  //  private val movieDAO: MovieDAO,
    private val popularity: String) :
    RxPagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val position = params.key ?: 1
        return apiService.getListOfMovies(popularity, position)
            .subscribeOn(schedulers.io())
            .map{toLoadResult(it,position)}
            .onErrorReturn { LoadResult.Error(it) }
    }
    private fun toLoadResult(data: MovieList, position: Int): LoadResult<Int, Movie> {
        return LoadResult.Page(
            data = data.results!!,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.totalPages) null else position + 1
        )
    }

}