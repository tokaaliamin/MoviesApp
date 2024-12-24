package com.example.moviesapp.domain.useCases

import com.example.moviesapp.data.repos.MoviesListRepo
import com.example.moviesapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieListUseCase {
    private val moviesListRepo by lazy { MoviesListRepo() }

    suspend operator fun invoke(pageNumber: Int): Flow<Result<List<Movie>>> {
        return moviesListRepo.discoverMovies(pageNumber)
    }
}