package com.example.moviesapp.data.remote.dataSources

import com.example.moviesapp.data.remote.models.MovieDetails
import com.example.moviesapp.data.remote.services.MovieDetailsService
import javax.inject.Inject

class MovieDetailsRemoteDataSourceImpl @Inject constructor(private val movieDetailsService: MovieDetailsService) :
    MovieDetailsRemoteDataSource {


    override suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        return movieDetailsService.fetchMovieDetails(movieId)
    }
}

interface MovieDetailsRemoteDataSource {
    suspend fun fetchMovieDetails(movieId: Int): MovieDetails
}