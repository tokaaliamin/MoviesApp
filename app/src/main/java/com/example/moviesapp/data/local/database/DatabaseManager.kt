package com.example.moviesapp.data.local.database

import androidx.room.Room
import com.example.moviesapp.utils.MyApplication

class DatabaseManager {
    private var db: AppDatabase? = null
    fun getDatabase(): AppDatabase? {
        if (db == null) {
            db = Room
                .databaseBuilder(MyApplication.appContext, AppDatabase::class.java, "moviesDB")
                .build()
        }
        return db
    }
}