package com.hesham.moviedbtask.domain.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.hesham.moviedbtask.di.RxSchedulers
import com.hesham.moviedbtask.domain.entities.Movie
import com.hesham.moviedbtask.domain.entities.MovieList
import com.hesham.moviedbtask.domain.entities.MovieRemoteKeys
import com.hesham.moviedbtask.domain.gateways.local.dao.MovieDAO
import com.hesham.moviedbtask.domain.gateways.local.dao.MovieRemoteKeysDAO
import com.hesham.moviedbtask.domain.gateways.remote.ApiService
import com.hesham.moviedbtask.ui.Constants
import io.reactivex.Single
import java.io.InvalidObjectException
import java.lang.Exception
import kotlin.reflect.jvm.internal.impl.load.java.Constant

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val schedulers: RxSchedulers,
    private val apiService: ApiService,
    private val movieDAO: MovieDAO,
    private val movieRemoteKeysDAO: MovieRemoteKeysDAO,
): RxRemoteMediator<Int,Movie>() {
    private var popularity: String  = Constants.TOP_RATED_MOVIE

    companion object {
        const val INVALID_PAGE = -1
    }
    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Movie>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(schedulers.io())
            .map{
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                        remoteKeys?.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        remoteKeys?.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if(page == INVALID_PAGE){
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                }else{
                    apiService.getListOfMovies(popularity,page)
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.page == it.totalPages) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }
            }
    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: MovieList): MovieList {
        try {
            if (loadType == LoadType.REFRESH) {
                movieRemoteKeysDAO.clearRemoteKeys()
                movieDAO.deleteAll()
            }
            val endOfPage = data.page == data.totalPages
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPage) null else page + 1
            val keys = data.results?.map {
                MovieRemoteKeys(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            keys?.let {
                movieRemoteKeysDAO.insertAll(it)
            }
            val movies = data.results?.map {
                val movie = it
                movie.filter_type = popularity
                movie
            }
            movies?.let {
                movieDAO.insertAll(it)
            }


        } catch (e:Exception) {
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            movieRemoteKeysDAO.remoteKeysByMovieId(repo.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            movieRemoteKeysDAO.remoteKeysByMovieId(movie.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDAO.remoteKeysByMovieId(id)
            }
        }
    }

    fun setPopularity(p:String){
        popularity = p
    }


}