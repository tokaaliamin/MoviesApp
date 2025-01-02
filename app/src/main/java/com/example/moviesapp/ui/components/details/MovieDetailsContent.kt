package com.example.moviesapp.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil3.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.ui.models.Genre
import com.example.moviesapp.ui.models.MovieDetails
import com.example.moviesapp.ui.models.getMovieDetailsTemp
import com.example.moviesapp.ui.theme.Dimens.Companion.dimens
import com.example.moviesapp.ui.theme.Yellow

@Composable
fun MovieDetailsContent(movie: MovieDetails?, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        val backdropHeight = MaterialTheme.dimens.heightBackdrop
        val imagesOverlay = MaterialTheme.dimens.heightBackdropOverlay
        AsyncImage(
            model = movie?.backdropUrl,
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
            .padding(horizontal = MaterialTheme.dimens.paddingMedium)
    ) {
        Row {
            AsyncImage(
                model = movie?.posterUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.placeholder_preview_small),
                modifier = Modifier
                    .height(MaterialTheme.dimens.heightPoster)
                    .width(MaterialTheme.dimens.widthPoster)
                    .padding(end = MaterialTheme.dimens.paddingMedium)
                    .clip(RoundedCornerShape(MaterialTheme.dimens.roundedCornerXLarge)),
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
            modifier = Modifier.size(MaterialTheme.dimens.sizeRatingStar)
        )
        Text(
            text = (rating ?: 0.0).toString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(MaterialTheme.dimens.paddingTiny)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreChipsList(genres: List<Genre>, modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingTiny)
    ) {
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
            .padding(top = MaterialTheme.dimens.paddingTiny)
            .background(
                Color.LightGray,
                RoundedCornerShape(MaterialTheme.dimens.roundedCornerMedium)
            )
            .padding(
                horizontal = MaterialTheme.dimens.paddingTiny,
                vertical = MaterialTheme.dimens.paddingMicro
            ),
        color = Color.DarkGray
    )
}

@Composable
fun Description(description: String) {
    Text(
        text = stringResource(R.string.overview),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = MaterialTheme.dimens.paddingMedium)
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