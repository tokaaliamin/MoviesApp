package com.example.moviesapp.features.list.domain

import com.example.moviesapp.features.list.data.remote.models.MoviesListResponse
import com.example.moviesapp.features.list.data.repos.MoviesListRepo

class MovieListUseCase {
    private val moviesListRepo by lazy { MoviesListRepo() }

    suspend operator fun invoke(pageNumber: Int): Result<MoviesListResponse> {
        return moviesListRepo.discoverMovies(pageNumber)
    }
}