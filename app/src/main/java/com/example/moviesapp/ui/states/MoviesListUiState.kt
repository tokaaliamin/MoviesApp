package com.example.moviesapp.ui.states

import com.example.moviesapp.ui.models.Movie
import com.example.moviesapp.ui.models.StringWrapper


data class MoviesListUiState(
    var movies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessageWrapper: StringWrapper? = null,
    val keyword: String? = null
)