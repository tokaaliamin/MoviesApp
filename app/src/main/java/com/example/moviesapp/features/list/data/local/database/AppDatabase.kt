package com.example.moviesapp.features.list.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapp.features.common.MyApplication
import com.example.moviesapp.features.list.data.local.daos.MovieDao
import com.example.moviesapp.features.list.data.local.models.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun movieDao():MovieDao
}