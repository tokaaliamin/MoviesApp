package com.example.moviesapp.ui.actions

sealed interface MovieDetailsUiActions {
    data class GetMovieDetails(val movieId: Int) : MovieDetailsUiActions
    data class RefreshMovieDetails(val movieId: Int) : MovieDetailsUiActions
}