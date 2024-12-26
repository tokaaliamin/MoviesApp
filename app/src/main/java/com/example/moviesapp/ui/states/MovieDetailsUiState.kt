package com.example.moviesapp.ui.states

import com.example.moviesapp.ui.models.MovieDetails
import com.example.moviesapp.ui.models.StringWrapper


data class MovieDetailsUiState(
    var movie: MovieDetails? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessageWrapper: StringWrapper? = null
)