package com.example.moviesapp.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.features.list.ui.components.ProgressBar
import com.example.moviesapp.ui.models.Genre
import com.example.moviesapp.ui.models.MovieDetails
import com.example.moviesapp.ui.models.getMovieDetailsTemp
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.ui.theme.Yellow
import com.example.moviesapp.ui.viewModels.MovieDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieDetailsViewModel: MovieDetailsViewModel = viewModel()
) {
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
                PullToRefreshBox(
                    isRefreshing = uiState.isRefreshing,
                    onRefresh = {

                    },
                    modifier = modifier.fillMaxSize()
                ) {
                    MovieDetailsContent(uiState.movie)
                }
            }
        }
    }
}

@Composable
private fun MovieDetailsContent(movie: MovieDetails?) {
    Box {
        val backdropHeight = 200.dp
        val imagesOverlay = 50.dp
        AsyncImage(
            model = movie?.backdropPath,
            placeholder = painterResource(R.drawable.placeholder_preview_small),
            contentDescription = null,
            modifier = Modifier
                .height(backdropHeight)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter
        )

        MovieDetailsInfo(
            movie,
            imagesOverlay,
            modifier = Modifier.padding(top = backdropHeight - imagesOverlay)
        )
    }
}

@Composable
fun MovieDetailsInfo(movie: MovieDetails?, imagesOverlay: Dp, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            AsyncImage(
                model = movie?.posterUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.placeholder_preview_small),
                modifier = Modifier
                    .height(200.dp)
                    .width(150.dp)
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
            )

            Column(modifier = Modifier.padding(top = imagesOverlay)) {
                Text(
                    text = movie?.title ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Rating(movie?.rating)

                if (movie?.genres.isNullOrEmpty().not())
                    GenreChipsList(movie?.genres!!)


            }

        }
        movie?.description?.let { desc ->
            Description(desc)
        }
    }
}

@Composable
fun Rating(rating: Double?, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Yellow,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = (rating ?: 0.0).toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreChipsList(genres: List<Genre>, modifier: Modifier = Modifier) {
    FlowRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        genres.forEach { genre ->
            GenreChipItem(genre.name ?: "")
        }
    }
}

@Composable
fun GenreChipItem(genre: String, modifier: Modifier = Modifier) {
    Text(
        text = genre,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
            .padding(top = 4.dp)
            .background(Color.LightGray, RoundedCornerShape(6.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp),
        color = Color.DarkGray
    )
}

@Composable
fun Description(description: String, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.overview),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp)
    )

    Text(
        text = description,
        style = MaterialTheme.typography.bodySmall
    )
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsContentPreview() {
    MovieDetailsContent(getMovieDetailsTemp())
}

