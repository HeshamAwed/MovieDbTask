package com.hesham.moviedbtask.domain.gateways.remote

import androidx.paging.PagingData
import com.hesham.moviedbtask.domain.entities.MovieList
import com.hesham.moviedbtask.domain.entities.Movie
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val movie = "3/movie/"
    }
    @GET("$movie{type}")
    fun getListOfMovies(
        @Path("type") type: String,
        @Query("page") page: Int
    ):Single<MovieList>

}
