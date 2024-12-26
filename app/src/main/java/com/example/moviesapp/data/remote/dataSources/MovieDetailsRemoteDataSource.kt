package com.example.moviesapp.data.remote.dataSources

import com.example.moviesapp.data.remote.RetrofitClient
import com.example.moviesapp.data.remote.models.MovieDetails
import com.example.moviesapp.data.remote.services.MovieDetailsService

class MovieDetailsRemoteDataSource() {
    private val movieDetailsService by lazy {
        RetrofitClient.getRetrofitClient().create(MovieDetailsService::class.java)
    }

    suspend fun fetchMovieDetails(movieId: Int): MovieDetails {
        return movieDetailsService.fetchMovieDetails(movieId)
    }
}