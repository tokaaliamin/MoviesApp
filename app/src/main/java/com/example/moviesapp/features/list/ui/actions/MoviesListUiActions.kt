package com.example.moviesapp.features.list.ui.actions

sealed interface MoviesListUiActions {
    data object OpenMovieDetails : MoviesListUiActions
    data object RefreshMoviesList : MoviesListUiActions
}