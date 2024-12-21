package com.example.moviesapp.features.list.data.repos

import com.example.moviesapp.features.list.data.remote.MoviesListRemoteDataSource
import com.example.moviesapp.features.list.data.remote.models.MoviesListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesListRepo() {
    private val moviesListRemoteDataSource by lazy { MoviesListRemoteDataSource() }

    suspend fun discoverMovies(pageNumber: Int): Result<MoviesListResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val result = moviesListRemoteDataSource.discoverMovies(pageNumber)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }


}