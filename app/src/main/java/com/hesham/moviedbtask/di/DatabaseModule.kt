package com.hesham.moviedbtask.di

import androidx.room.Room
import com.hesham.moviedbtask.data.local.db.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single { get<AppDatabase>().movieDAO() }
}
