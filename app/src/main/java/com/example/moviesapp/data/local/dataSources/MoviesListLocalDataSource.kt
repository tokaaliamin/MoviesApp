package com.example.moviesapp.data.local.dataSources

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.moviesapp.data.local.database.AppDatabase
import com.example.moviesapp.data.models.Movie
import javax.inject.Inject

class MoviesListLocalDataSource @Inject constructor(private val database: AppDatabase) {

    fun discoverMovies(): PagingSource<Int, Movie> {
        return database.movieDao().discoverMovies()
    }

    fun cacheMovies(movies: List<Movie>) {
        database.movieDao().insertAll(movies)
    }

    fun clearMovies() {
        database.movieDao().deleteAllMovies()
    }

    fun searchMovies(keyword: String, pageNumber: Int): PagingSource<Int, Movie>? {
        return database.movieDao().findByTitle(keyword)
    }

    suspend fun updateMoviesData(isRefresh: Boolean, movies: List<Movie>) {
        database.withTransaction {
            if (isRefresh) {
                clearMovies()
            }

            // Insert new users into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            cacheMovies(movies)
        }
    }
    /*  suspend fun searchMovies(
          keyword:String,
          pageNumber: Int
      ): MoviesListResponse {
          return moviesListService.searchMovies(keyword,pageNumber)
      }*/

}