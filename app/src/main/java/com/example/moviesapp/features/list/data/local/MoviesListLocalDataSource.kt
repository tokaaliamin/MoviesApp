package com.example.moviesapp.features.list.data.local

import com.example.moviesapp.features.list.data.remote.models.MoviesListResponse
import com.example.moviesapp.features.list.data.remote.services.MoviesListService
import com.example.moviesapp.features.common.data.remote.RetrofitClient
import com.example.moviesapp.features.list.data.local.database.DatabaseManager
import com.example.moviesapp.features.list.data.local.models.Movie

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