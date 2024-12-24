package com.example.moviesapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.R
import com.example.moviesapp.data.remote.models.ErrorResponse
import com.example.moviesapp.domain.useCases.MovieListUseCase
import com.example.moviesapp.domain.useCases.PostersUseCase
import com.example.moviesapp.domain.useCases.SearchMoviesUseCase
import com.example.moviesapp.ui.actions.MoviesListUiActions
import com.example.moviesapp.ui.models.Movie
import com.example.moviesapp.ui.models.StringWrapper
import com.example.moviesapp.ui.states.MoviesListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import kotlin.time.Duration.Companion.milliseconds

class MoviesListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MoviesListUiState())
    val uiState: StateFlow<MoviesListUiState> = _uiState
    private var runningJob: Job? = null

    init {
        discoverMovies(1)
    }

    fun onAction(action: MoviesListUiActions) {
        when (action) {
            MoviesListUiActions.OpenMovieDetails -> TODO()
            MoviesListUiActions.RefreshMoviesList -> {
                _uiState.update { it.copy(isRefreshing = true) }
                discoverMovies(1)
            }

            is MoviesListUiActions.Search -> {
                when (action.keyword) {
                    null, "" -> onAction(MoviesListUiActions.ClearSearch)

                    else -> searchMovies(action.keyword, 1)

                }
            }

            MoviesListUiActions.ClearSearch -> {
                resetSearch()
            }
        }
    }


    private fun resetSearch() {
        _uiState.update { it.copy(keyword = null) }
        onAction(MoviesListUiActions.RefreshMoviesList)
    }

    @OptIn(FlowPreview::class)
    private fun discoverMovies(pageNumber: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            MovieListUseCase().invoke(pageNumber)
                .flowOn(Dispatchers.IO)
                .debounce(2000.milliseconds)
                .onCompletion {
                    _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
                }
                .collectLatest { result ->
                    handleMoviesListResult(result)
                }
        }
    }

    private fun searchMovies(keyword: String, pageNumber: Int) {
        runningJob?.cancel()
        runningJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, keyword = keyword) }
            val result = SearchMoviesUseCase().invoke(keyword, pageNumber)
            _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
            handleMoviesListResult(result)
        }
    }

    private fun handleMoviesListResult(result: Result<List<com.example.moviesapp.domain.models.Movie>>) {
        when {
            result.isSuccess -> {
                result.getOrNull()?.map { movie ->
                    Movie(
                        movie.id,
                        movie.title,
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