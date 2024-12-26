package com.example.moviesapp.domain.useCases

import com.example.moviesapp.data.repos.MovieDetailsRepo
import com.example.moviesapp.domain.models.MovieDetails

class MovieDetailsUseCase {
    private val movieDetailsRepo by lazy { MovieDetailsRepo() }

    suspend operator fun invoke(movieId: Int): Result<MovieDetails> {
        return movieDetailsRepo.fetchMovieDetails(movieId)
    }
}