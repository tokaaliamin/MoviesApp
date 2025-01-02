package com.example.moviesapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviesapp.domain.models.toUiMovie
import com.example.moviesapp.domain.useCases.MovieListUseCase
import com.example.moviesapp.ui.actions.MoviesListUiActions
import com.example.moviesapp.ui.models.Movie
import com.example.moviesapp.ui.states.MoviesListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val useCase: MovieListUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesListUiState())
    val uiState: StateFlow<MoviesListUiState> = _uiState

    private var _moviesListFlow: Flow<PagingData<Movie>> = useCase.invoke()
        .map { it.map { domainMovie -> domainMovie.toUiMovie() } }
        .cachedIn(viewModelScope)
    val moviesListFlow: Flow<PagingData<Movie>>
        get() = _moviesListFlow


    fun onAction(action: MoviesListUiActions) {
        when (action) {
            is MoviesListUiActions.Search -> {
                when (action.keyword) {
                    null, "" -> onAction(MoviesListUiActions.ClearSearch)

                    else -> {
                        viewModelScope.launch {
                            _uiState.update { it.copy(keyword = action.keyword) }
                            useCase.updateSearchQuery(action.keyword)
                        }
                    }
                }
            }

            MoviesListUiActions.ClearSearch -> {
                viewModelScope.launch {
                    _uiState.update { it.copy(keyword = null) }
                    useCase.updateSearchQuery("")
                }
            }
        }
    }

}