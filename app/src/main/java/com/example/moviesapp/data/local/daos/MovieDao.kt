package com.example.moviesapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.data.local.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY popularity DESC")
    fun getAll():List<Movie>

    @Query("SELECT * FROM movie WHERE title LIKE :keyword ORDER BY popularity DESC")
    fun findByTitle(keyword:String):List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movie")
    fun deleteAllMovies()

}