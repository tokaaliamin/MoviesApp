package com.example.moviesapp.features.list.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.features.list.data.local.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll():List<Movie>

    @Query("SELECT * FROM movie WHERE title LIKE :keyword")
    fun findByTitle(keyword:String):List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movie")
    fun deleteAllMovies()

}