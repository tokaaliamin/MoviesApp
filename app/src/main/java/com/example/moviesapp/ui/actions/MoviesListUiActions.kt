package com.example.moviesapp.ui.actions

sealed interface MoviesListUiActions {
    data class Search(val keyword: String?) : MoviesListUiActions
    data object ClearSearch : MoviesListUiActions
}