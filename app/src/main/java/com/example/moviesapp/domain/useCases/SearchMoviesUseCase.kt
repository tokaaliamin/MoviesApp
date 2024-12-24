package com.example.moviesapp.domain.useCases

import com.example.moviesapp.data.repos.MoviesListRepo
import com.example.moviesapp.domain.models.Movie

class SearchMoviesUseCase {
    private val moviesListRepo by lazy { MoviesListRepo() }

    suspend operator fun invoke(keyword:String,pageNumber: Int): Result<List<Movie>> {
        return moviesListRepo.searchMovies(keyword,pageNumber)
    }
}