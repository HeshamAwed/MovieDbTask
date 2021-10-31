package com.hesham.moviedbtask.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.hesham.moviedbtask.data.model.MovieModel

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movie WHERE filter_type =:queryString ORDER BY voteAverage desc")
     fun getMovieList(queryString: String): PagingSource<Int,MovieModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<MovieModel>)

    @Delete
    suspend fun deleteMovie(movie: MovieModel)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteMovie(id: String)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()


}