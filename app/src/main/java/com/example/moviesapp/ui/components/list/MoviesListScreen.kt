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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesapp.R
import com.example.moviesapp.ui.actions.MoviesListUiActions
import com.example.moviesapp.ui.components.ErrorPlaceholder
import com.example.moviesapp.ui.components.NoDataPlaceholder
import com.example.moviesapp.ui.components.list.MoviesList
import com.example.moviesapp.ui.theme.Dimens.Companion.dimens
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.ui.viewModels.MoviesListViewModel
import com.example.moviesapp.utils.ErrorParser.getLoadStateError
import com.example.moviesapp.utils.ErrorParser.isLoadStateError


@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun MoviesListScreen(
    onMovieClicked: (Int?) -> Unit = {},
    modifier: Modifier = Modifier,
    moviesListViewModel: MoviesListViewModel = hiltViewModel()
) {
    MoviesAppTheme {
        val uiState by moviesListViewModel.uiState.collectAsStateWithLifecycle()
        val pagingItems = moviesListViewModel.moviesListFlow.collectAsLazyPagingItems()

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
            val isLoading = pagingItems.loadState.mediator?.append is LoadState.Loading ||
                    pagingItems.loadState.source.append is LoadState.Loading
            val isError = isLoadStateError(pagingItems.loadState)
            val isEmpty = pagingItems.itemCount == 0 && !isLoading && !isError
            val isRefreshing = pagingItems.loadState.refresh is LoadState.Loading

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (isLoading)
                    ProgressBar()
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        pagingItems.refresh()
                    },
                    modifier = modifier.fillMaxSize()
                ) {
                    when {
                        isError && pagingItems.itemCount == 0 -> {
                            getLoadStateError(pagingItems.loadState)?.let { error ->
                                ErrorPlaceholder(
                                    error,
                                    { pagingItems.retry() }
                                )
                            }
                        }

                        isEmpty ->
                            NoDataPlaceholder()

                        pagingItems.itemCount > 0 ->
                            MoviesList(
                                pagingItems,
                                onMovieClicked
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
            .heightIn(min = MaterialTheme.dimens.heightSearchBar),
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
