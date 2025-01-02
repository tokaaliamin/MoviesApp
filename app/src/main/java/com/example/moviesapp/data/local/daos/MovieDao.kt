package com.example.moviesapp.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.data.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun fetchMoviesList(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("DELETE FROM movie")
    fun deleteAllMovies()

}