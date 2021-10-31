package com.hesham.moviedbtask.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hesham.moviedbtask.data.local.dao.MovieDAO
import com.hesham.moviedbtask.data.model.MovieModel

@Database(entities = [MovieModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
}
