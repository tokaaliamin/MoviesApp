package com.example.moviesapp.domain.useCases

import androidx.paging.PagingData
import com.example.moviesapp.data.repos.MoviesListRepo
import com.example.moviesapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieListUseCase {
    private val moviesListRepo by lazy { MoviesListRepo() }

    operator fun invoke(): Flow<PagingData<Movie>> {
        return moviesListRepo.getMovies()
    }

    suspend fun updateSearchQuery(keyword: String) {
        moviesListRepo.updateSearchQuery(keyword)
    }
}