package com.example.moviesapp.data.repos

import com.example.moviesapp.data.remote.dataSources.MovieDetailsRemoteDataSource
import com.example.moviesapp.data.remote.models.toDomainMovieDetails
import com.example.moviesapp.domain.models.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieDetailsRepo @Inject constructor(private val movieDataRemoteDataSource: MovieDetailsRemoteDataSource) {

    suspend fun fetchMovieDetails(movieId: Int): Result<MovieDetails> {
        return withContext(Dispatchers.IO) {
            try {
                val result = movieDataRemoteDataSource.fetchMovieDetails(movieId)
                Result.success(result.toDomainMovieDetails())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}