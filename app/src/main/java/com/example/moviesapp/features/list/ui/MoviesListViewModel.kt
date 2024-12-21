package com.example.moviesapp.features.list.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.features.common.domain.PostersUseCase
import com.example.moviesapp.features.list.data.remote.models.ErrorResponse
import com.example.moviesapp.features.list.domain.MovieListUseCase
import com.example.moviesapp.features.list.ui.actions.MoviesListUiActions
import com.example.moviesapp.features.list.ui.models.Movie
import com.example.moviesapp.features.list.ui.states.MoviesListUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.HttpException

class MoviesListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MoviesListUiState())
    val uiState: StateFlow<MoviesListUiState> = _uiState

    /*   private val _movies = mutableStateListOf<Movie>().apply {
           addAll(
               listOf(
                   getMovieTemp(), getMovieTemp()
               )
           )
       }
   */

    init {
        discoverMovies(1)
    }

    fun onAction(action:MoviesListUiActions){
        when(action){
            MoviesListUiActions.OpenMovieDetails -> TODO()
            MoviesListUiActions.RefreshMoviesList -> {
                _uiState.update { it.copy(isRefreshing = true) }
                discoverMovies(1)
            }
        }
    }

    public fun discoverMovies(pageNumber: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = MovieListUseCase().invoke(pageNumber)
            _uiState.update { it.copy(isLoading = false, isRefreshing = false) }

            when {
                result.isSuccess -> {
                    result.getOrNull()?.movies?.map { movie ->
                        Movie(
                            movie.id,
                            movie.originalTitle,
                            PostersUseCase().invoke(movie.posterPath),
                            movie.releaseDate
                        )
                    }?.apply {
                        _uiState.update {
                            it.copy(movies = this)
                        }
                    }
                }

                result.isFailure -> {
                    val errorMessage = when (val throwable = result.exceptionOrNull()) {
                        is HttpException -> {
                            // Get error body as a string
                            val errorBody = throwable.response()?.errorBody()?.string()
                            if (!errorBody.isNullOrEmpty()) {
                                try {
                                    val errorResponse =
                                        Json.decodeFromString<ErrorResponse>(errorBody)
                                    errorResponse.statusMessage

                                } catch (e: Exception) {
                                    "Failed to parse error response"
                                }
                            } else {
                                "Failed to parse error response"
                            }
                        }

                        else ->
                            "Failed to parse error response"
                    }
                    _uiState.update {
                        it.copy(errorMessage = errorMessage)
                    }
                }
            }

        }
    }
}