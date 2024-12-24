package com.example.moviesapp.features.list.domain

import com.example.moviesapp.features.list.data.remote.models.MoviesListResponse
import com.example.moviesapp.features.list.data.repos.MoviesListRepo
import com.example.moviesapp.features.list.domain.movies.Movie
import kotlinx.coroutines.flow.Flow

class MovieListUseCase {
    private val moviesListRepo by lazy { MoviesListRepo() }

    suspend operator fun invoke(pageNumber: Int): Flow<Result<List<Movie>>> {
        return moviesListRepo.discoverMovies(pageNumber)
    }
}