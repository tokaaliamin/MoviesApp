package com.example.moviesapp.features.list.ui.states

import com.example.moviesapp.features.common.ui.models.StringWrapper
import com.example.moviesapp.features.list.ui.models.Movie


data class MoviesListUiState(
    var movies: List<Movie>? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessageWrapper: StringWrapper? = null,
    val keyword: String? = null
)