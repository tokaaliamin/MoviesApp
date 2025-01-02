package com.example.moviesapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.models.MovieDetails
import com.example.moviesapp.domain.models.toUiMovieDetails
import com.example.moviesapp.domain.useCases.MovieDetailsUseCase
import com.example.moviesapp.ui.actions.MovieDetailsUiActions
import com.example.moviesapp.ui.states.MovieDetailsUiState
import com.example.moviesapp.utils.ErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val useCase: MovieDetailsUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    fun onAction(action: MovieDetailsUiActions) {
        when (action) {
            is MovieDetailsUiActions.GetMovieDetails -> {
                _uiState.update { it.copy(isLoading = true) }
                getMovieDetails(action.movieId)
            }

            is MovieDetailsUiActions.RefreshMovieDetails -> {
                _uiState.update { it.copy(isLoading = true, isRefreshing = true) }
                getMovieDetails(action.movieId)
            }
        }
    }

    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val result = useCase.invoke(movieId)
            _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
            handleMoviesListResult(result)
        }
    }

    private fun handleMoviesListResult(result: Result<MovieDetails>) {
        when {
            result.isSuccess -> {
                result.getOrNull()?.toUiMovieDetails()
                    ?.apply {
                        _uiState.update {
                            it.copy(movie = this)
                        }
                    }
            }

            result.isFailure -> {
                result.exceptionOrNull()?.let { throwable ->
                    _uiState.update {
                        it.copy(errorMessageWrapper = ErrorParser.parseThrowable(throwable))
                    }
                }

            }
        }

    }
}