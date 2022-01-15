package com.hesham.moviedbtask.domain.gateways.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hesham.moviedbtask.domain.entities.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<MovieRemoteKeys>)

    @Query("SELECT * FROM movieremotekeys WHERE movieId = :movieId")
    fun remoteKeysByMovieId(movieId: Long): MovieRemoteKeys?

    @Query("DELETE FROM movieremotekeys")
    fun clearRemoteKeys()

}