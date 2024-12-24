package com.example.moviesapp.data.remote.dataSources

import com.example.moviesapp.data.remote.RetrofitClient
import com.example.moviesapp.data.remote.models.MoviesListResponse
import com.example.moviesapp.data.remote.services.MoviesListService

class MoviesListRemoteDataSource() {
    private val moviesListService by lazy {
        RetrofitClient.getRetrofitClient().create(MoviesListService::class.java)
    }

    suspend fun discoverMovies(
        pageNumber: Int
    ): MoviesListResponse {
        return moviesListService.discoverMovies(pageNumber)
    }
    suspend fun searchMovies(
        keyword:String,
        pageNumber: Int
    ): MoviesListResponse {
        return moviesListService.searchMovies(keyword,pageNumber)
    }

}