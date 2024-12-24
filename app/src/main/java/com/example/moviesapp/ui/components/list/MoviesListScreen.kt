package com.example.moviesapp.features.list.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.R
import com.example.moviesapp.ui.actions.MoviesListUiActions
import com.example.moviesapp.ui.components.ErrorPlaceholder
import com.example.moviesapp.ui.components.NoDataPlaceholder
import com.example.moviesapp.ui.components.list.MoviesList
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.ui.viewModels.MoviesListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun MoviesListScreen(
    modifier: Modifier = Modifier,
    moviesListViewModel: MoviesListViewModel = viewModel()
) {
    MoviesAppTheme {
        val uiState by moviesListViewModel.uiState.collectAsStateWithLifecycle()

        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                SearchBar(currentKeyword = uiState.keyword, onValueChange = { keyword ->
                    moviesListViewModel.onAction(
                        if (keyword.isBlank())
                            MoviesListUiActions.ClearSearch
                        else
                            MoviesListUiActions.Search(keyword)
                    )
                }, onClear = { moviesListViewModel.onAction(MoviesListUiActions.ClearSearch) })
            }
        ) { innerPadding ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .padding(innerPadding)) {
                if (uiState.isLoading)
                    ProgressBar()
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
                                uiState.movies!!
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
}

@Composable
fun SearchBar(
    currentKeyword: String?,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = currentKeyword ?: "",
        placeholder = { Text(stringResource(R.string.search)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = { onClear.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }
        },
        onValueChange = { keyword ->
            onValueChange.invoke(keyword.trim())
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    LinearProgressIndicator(modifier = modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar(currentKeyword = "", onValueChange = {}, onClear = {})
}
