package com.example.moviesapp.features.list.ui.states

import com.example.moviesapp.features.list.ui.models.Movie


data class MoviesListUiState(
    var movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)