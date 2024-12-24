package com.example.moviesapp.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.moviesapp.ui.states.MovieDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MovieDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState
}