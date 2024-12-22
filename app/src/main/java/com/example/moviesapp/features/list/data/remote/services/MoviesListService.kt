package com.example.moviesapp.features.list.data.remote.services

import com.example.moviesapp.features.list.data.remote.models.MoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesListService {
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("page") pageNumber: Int,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("language") language: String = "en-US"
    ): MoviesListResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") keyword: String,
        @Query("page") pageNumber: Int,
        @Query("language") language: String = "en-US"
    ): MoviesListResponse

}
