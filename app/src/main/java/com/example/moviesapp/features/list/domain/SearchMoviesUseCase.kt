package com.example.moviesapp.features.list.domain

import com.example.moviesapp.features.list.data.remote.models.MoviesListResponse
import com.example.moviesapp.features.list.data.repos.MoviesListRepo
import com.example.moviesapp.features.list.domain.movies.Movie
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase {
    private val moviesListRepo by lazy { MoviesListRepo() }

    suspend operator fun invoke(keyword:String,pageNumber: Int): Result<List<Movie>> {
        return moviesListRepo.searchMovies(keyword,pageNumber)
    }
}