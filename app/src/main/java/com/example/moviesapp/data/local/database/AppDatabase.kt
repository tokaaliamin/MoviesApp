package com.example.moviesapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.data.local.daos.MovieDao
import com.example.moviesapp.data.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun movieDao(): MovieDao
}