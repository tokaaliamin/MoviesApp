package com.example.moviesapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.R
import com.example.moviesapp.data.remote.models.ErrorResponse
import com.example.moviesapp.domain.models.MovieDetails
import com.example.moviesapp.domain.models.toUiMovieDetails
import com.example.moviesapp.domain.useCases.MovieDetailsUseCase
import com.example.moviesapp.ui.actions.MovieDetailsUiActions
import com.example.moviesapp.ui.models.StringWrapper
import com.example.moviesapp.ui.states.MovieDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.HttpException
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
                val errorMessageWrapper = when (val throwable = result.exceptionOrNull()) {
                    is HttpException -> {
                        // Get error body as a string
                        val errorBody = throwable.response()?.errorBody()?.string()
                        if (!errorBody.isNullOrEmpty()) {
                            try {
                                val errorResponse =
                                    Json.decodeFromString<ErrorResponse>(errorBody)
                                StringWrapper(message = errorResponse.statusMessage)
                            } catch (e: Exception) {
                                StringWrapper(stringInt = R.string.failed_to_parse_error_response)
                            }
                        } else {
                            StringWrapper(stringInt = R.string.failed_to_parse_error_response)
                        }
                    }

                    else ->
                        StringWrapper(stringInt = R.string.failed_to_parse_error_response)
                }
                _uiState.update {
                    it.copy(errorMessageWrapper = errorMessageWrapper)
                }
            }
        }

    }
}