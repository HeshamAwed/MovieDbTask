package com.hesham.moviedbtask.ui.movies_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.hesham.moviedbtask.base.BaseViewModel
import com.hesham.moviedbtask.data.model.MovieModel
import com.hesham.moviedbtask.data.repos.MovieRepos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MoviesListViewModel constructor(var movieRepos: MovieRepos) : BaseViewModel() {
    private var pagingSource: PagingSource<Int, MovieModel>? = null
    var isRefreshing = MutableLiveData<Boolean>(false)
    var isEmptyList = MutableLiveData<Boolean>(false)

    fun getMovies(popularity: String): Flow<PagingData<MovieModel>> {

        val pager = Pager(
            config = movieRepos.getDefaultPageConfig(),
            pagingSourceFactory = {
                movieRepos.getMovieDataSource(popularity).apply {
                    pagingSource = this
                }
            }
        ).flow

        return pager.cachedIn(viewModelScope)
    }

    fun handleLoadStates(combinedLoadStates: CombinedLoadStates, itemCount: Int) {
        when {
            combinedLoadStates.refresh is LoadState.Error || combinedLoadStates.append is LoadState.Error || combinedLoadStates.prepend is LoadState.Error -> {
                hideLoadRefresh()
                isEmptyList.postValue(itemCount == 0)
                val error = when {
                    combinedLoadStates.refresh is LoadState.Error -> combinedLoadStates.refresh as LoadState.Error
                    combinedLoadStates.append is LoadState.Error -> combinedLoadStates.append as LoadState.Error
                    combinedLoadStates.prepend is LoadState.Error -> combinedLoadStates.prepend as LoadState.Error
                    else -> null
                }
                if (error != null) {
                    viewModelScope.launch {
                        onError(error.error)
                    }
                }
            }

            combinedLoadStates.refresh is LoadState.NotLoading && combinedLoadStates.append is LoadState.NotLoading && combinedLoadStates.prepend is LoadState.NotLoading -> {
                hideLoadRefresh()
                isEmptyList.postValue(itemCount == 0)
            }
            combinedLoadStates.refresh is LoadState.Loading && combinedLoadStates.append is LoadState.Loading && combinedLoadStates.prepend is LoadState.Loading -> {
                isEmptyList.postValue(itemCount == 0)
                if (itemCount == 0) {
                    showLoading()
                }
            }
        }
    }

    override fun onError(throwable: Throwable) {
        super.onError(throwable)
        // reset load
        hideLoadRefresh()
    }

    fun reload() {
        showLoading()
        pagingSource?.invalidate()
    }

    fun doRefreshMovies() {
        isRefreshing.value = true
        pagingSource?.invalidate()
    }
    private fun hideLoadRefresh() {
        hideLoading()
        isRefreshing.postValue(false)
    }
}
