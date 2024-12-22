package com.example.moviesapp.features.list.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.features.common.ui.components.ErrorPlaceholder
import com.example.moviesapp.features.common.ui.components.NoDataPlaceholder
import com.example.moviesapp.features.list.ui.actions.MoviesListUiActions
import com.example.moviesapp.features.list.ui.viewModels.MoviesListViewModel
import com.example.moviesapp.ui.theme.MoviesAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun MoviesListScreen(
    modifier: Modifier = Modifier,
    moviesListViewModel: MoviesListViewModel = viewModel()
) {
    MoviesAppTheme {
        val uiState by moviesListViewModel.uiState.collectAsStateWithLifecycle()

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = {
                    moviesListViewModel.onAction(MoviesListUiActions.RefreshMoviesList)
                },
                modifier = modifier.fillMaxSize()
            ) {
                when {
                    uiState.movies != null && uiState.movies!!.isEmpty() ->
                        NoDataPlaceholder()

                    uiState.movies != null ->
                        MoviesList(
                            uiState.movies!!, Modifier.padding(innerPadding)
                        )

                    uiState.errorMessageWrapper != null ->
                        ErrorPlaceholder(
                            uiState.errorMessageWrapper!!,
                            { moviesListViewModel.onAction(MoviesListUiActions.RefreshMoviesList) }
                        )
                }
            }
        }
    }
}
