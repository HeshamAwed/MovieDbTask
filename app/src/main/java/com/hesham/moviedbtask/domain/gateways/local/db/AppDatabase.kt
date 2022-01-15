package com.hesham.moviedbtask.domain.gateways.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hesham.moviedbtask.domain.gateways.local.dao.MovieDAO
import com.hesham.moviedbtask.domain.entities.Movie
import com.hesham.moviedbtask.domain.entities.MovieRemoteKeys
import com.hesham.moviedbtask.domain.gateways.local.dao.MovieRemoteKeysDAO

@Database(entities = [Movie::class,MovieRemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
    abstract fun movieRemoteKeys(): MovieRemoteKeysDAO
}
