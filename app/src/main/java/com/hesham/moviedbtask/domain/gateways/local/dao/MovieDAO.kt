package com.hesham.moviedbtask.domain.gateways.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hesham.moviedbtask.domain.entities.Movie

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movie WHERE filter_type =:queryString ORDER BY voteAverage desc")
    fun getMovieList(queryString: String): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertAll(list: List<Movie>)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movie WHERE id = :id")
    suspend fun deleteMovie(id: String)

    @Query("DELETE FROM movie")
     fun deleteAll()
}
