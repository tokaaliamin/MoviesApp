package com.example.moviesapp.data.local.dataSources

import com.example.moviesapp.data.local.database.DatabaseManager
import com.example.moviesapp.data.local.models.Movie

class MoviesListLocalDataSource() {
    private val databaseManager by lazy {
        DatabaseManager()
    }

    suspend fun discoverMovies(): List<Movie>? {
        return databaseManager.getDatabase()?.movieDao()?.getAll()
    }

    fun cacheMovies(movies: List<Movie>) {
        databaseManager.getDatabase()?.movieDao()?.insertAll(movies)
    }

    fun clearMovies() {
        databaseManager.getDatabase()?.movieDao()?.deleteAllMovies()
    }

    fun searchMovies(keyword: String, pageNumber: Int): List<Movie>? {
        return databaseManager.getDatabase()?.movieDao()?.findByTitle("%$keyword%")
    }
    /*  suspend fun searchMovies(
          keyword:String,
          pageNumber: Int
      ): MoviesListResponse {
          return moviesListService.searchMovies(keyword,pageNumber)
      }*/

}