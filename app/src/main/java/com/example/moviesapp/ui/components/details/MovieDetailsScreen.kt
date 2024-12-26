package com.example.moviesapp.ui.components.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.features.list.ui.components.ProgressBar
import com.example.moviesapp.ui.actions.MovieDetailsUiActions
import com.example.moviesapp.ui.components.ErrorPlaceholder
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.ui.viewModels.MovieDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieId: Int? = null,
    movieDetailsViewModel: MovieDetailsViewModel = viewModel()
) {
    movieId?.let { id ->
        movieDetailsViewModel.onAction(MovieDetailsUiActions.GetMovieDetails(id))
    }
    MoviesAppTheme {
        val uiState by movieDetailsViewModel.uiState.collectAsStateWithLifecycle()

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (uiState.isLoading)
                    ProgressBar()
                if ((uiState.isLoading.not() || uiState.isRefreshing) && uiState.errorMessageWrapper == null)
                    PullToRefreshBox(
                        isRefreshing = uiState.isRefreshing,
                        onRefresh = {
                            movieId?.let { id ->
                                movieDetailsViewModel.onAction(
                                    MovieDetailsUiActions.RefreshMovieDetails(
                                        id
                                    )
                                )
                            }
                        },
                        modifier = modifier.fillMaxSize()
                    ) {
                        MovieDetailsContent(uiState.movie)
                    }

                if (uiState.errorMessageWrapper != null)
                    ErrorPlaceholder(
                        uiState.errorMessageWrapper!!,
                        {
                            movieId?.let { id ->
                                movieDetailsViewModel.onAction(
                                    MovieDetailsUiActions.RefreshMovieDetails(
                                        id
                                    )
                                )
                            }
                        }
                    )

            }
        }
    }
}




