package com.example.moviesapp.data.remote.dataSources

import com.example.moviesapp.data.remote.models.MovieDetails
import com.example.moviesapp.data.remote.services.MovieDetailsService
import javax.inject.Inject

class MovieDetailsRemoteDataSource @Inject constructor(private val movieDetailsService: MovieDetailsService) :
    MovieDetailsDataSource {
    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        return movieDetailsService.fetchMovieDetails(movieId)
    }
}

interface MovieDetailsDataSource {
    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
}