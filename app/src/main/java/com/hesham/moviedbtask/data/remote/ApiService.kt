package com.hesham.moviedbtask.data.remote

import com.hesham.moviedbtask.data.model.MovieListModel
import com.hesham.moviedbtask.data.model.MovieModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    companion object{
        const val movie = "3/movie/"
    }
    @GET("$movie{type}")
   suspend fun getListOfMovies(
        @Path("type") type:String,
        @Query("page") page : Int
    ) : MovieListModel

    @GET("$movie{id}")
  suspend  fun getMovieDetails(
        @Path("id") id:Int,
    ) :Call<MovieModel>
}