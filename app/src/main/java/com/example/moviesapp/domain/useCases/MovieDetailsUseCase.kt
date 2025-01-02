package com.example.moviesapp.domain.useCases

import com.example.moviesapp.data.repos.MovieDetailsRepo
import com.example.moviesapp.domain.models.MovieDetails
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(private val movieDetailsRepo: MovieDetailsRepo) {

    suspend operator fun invoke(movieId: Int): Result<MovieDetails> {
        return movieDetailsRepo.fetchMovieDetails(movieId)
    }
}