package com.example.moviesapp.ui.components.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.example.moviesapp.ui.models.Movie
import com.example.moviesapp.ui.models.getMovieTemp
import com.example.moviesapp.ui.theme.Dimens.Companion.dimens

@Composable
fun MoviesList(
    movies: LazyPagingItems<Movie>,
    onMovieClicked: (Int?) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(MaterialTheme.dimens.moviesGridColumnsCount),
        contentPadding = PaddingValues(MaterialTheme.dimens.paddingMedium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingMedium),
        state = rememberLazyGridState()
    ) {
        items(movies.itemCount,
            key = movies.itemKey { it.id!! }) { index ->
            movies[index]?.let { MovieItem(it, onMovieClicked) }
        }

    }
}

@Composable
fun MovieItem(movie: Movie, onMovieClicked: (Int?) -> Unit = {}, modifier: Modifier = Modifier) {
    Card(modifier = modifier.clickable { onMovieClicked.invoke(movie.id) }) {
        Column {
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(MaterialTheme.dimens.heightCard)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movie.title ?: "",
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.paddingSmall),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = movie.releaseDate ?: "",
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.dimens.paddingSmall)
                    .padding(bottom = MaterialTheme.dimens.paddingSmall),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MovieItemPreview() {
    MovieItem(
        getMovieTemp()
    )
}