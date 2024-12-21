package com.example.moviesapp.features.list.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.features.list.ui.actions.MoviesListUiActions
import com.example.moviesapp.features.list.ui.models.Movie
import com.example.moviesapp.features.list.ui.models.getMovieTemp
import com.example.moviesapp.ui.theme.MoviesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesListScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesListScreen(
    modifier: Modifier = Modifier,
    moviesListViewModel: MoviesListViewModel = viewModel()
) {
    MoviesAppTheme {
        val uiState by moviesListViewModel.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current
        //TODO convert to snackbar
        LaunchedEffect(uiState.errorMessage) {
            uiState.errorMessage?.let {
                Toast.makeText(context, uiState.errorMessage ?: "", Toast.LENGTH_SHORT).show()
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = {
                    moviesListViewModel.onAction(MoviesListUiActions.RefreshMoviesList)
                },
                modifier = modifier.fillMaxSize()
            ) {
                if (uiState.movies.isEmpty())
                    EmptyMoviesList()
                else
                    MoviesList(
                        uiState.movies, Modifier.padding(innerPadding)
                    )
            }
        }
    }
}


@Composable
fun EmptyMoviesList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.placeholder_empty_list),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(R.string.no_movies_has_been_found), style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun MoviesList(movies: List<Movie>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = rememberLazyGridState()
    ) {
        items(movies) { movie ->
            MovieItem(movie)
        }

    }
}

@Composable
fun MovieItem(movie: Movie, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = movie.title ?: "",
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = movie.releaseDate ?: "",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyMoviesListPreview() {
   EmptyMoviesList()
}


@Preview(showBackground = true)
@Composable
fun MoviesListPreview() {
    MoviesList(
        listOf(
            getMovieTemp(),
            getMovieTemp(),
            getMovieTemp()
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    MovieItem(
        getMovieTemp()
    )
}

