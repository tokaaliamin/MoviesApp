package com.example.moviesapp.features.list.data.remote

import com.example.moviesapp.features.list.data.remote.models.MoviesListResponse
import com.example.moviesapp.features.list.data.remote.services.MoviesListService
import com.example.moviesapp.features.common.data.remote.RetrofitClient

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