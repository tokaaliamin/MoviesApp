package com.example.moviesapp.data.remote.services

import com.example.moviesapp.data.remote.models.MoviesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesListService {
    @GET("movie/top_rated")
    suspend fun fetchMoviesList(
        @Query("page") pageNumber: Int,
        @Query("language") language: String = "en-US"
    ): MoviesListResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") keyword: String,
        @Query("page") pageNumber: Int,
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = false
    ): MoviesListResponse

}
