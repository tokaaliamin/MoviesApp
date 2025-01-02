package com.example.moviesapp.domain.models


import com.example.moviesapp.domain.useCases.ImagesUseCase
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val backdropPathSuffix: String? = null,
    val genres: List<Genre>? = null,
    val id: Int? = null,
    val title: String? = null,
    val overview: String? = null,
    val posterPathSuffix: String? = null,
    val rating: Double? = null
)

fun MovieDetails.toUiMovieDetails() = com.example.moviesapp.ui.models.MovieDetails(
    id,
    title,
    ImagesUseCase().invoke(posterPathSuffix, ImageSize.POSTER),
    ImagesUseCase().invoke(backdropPathSuffix, ImageSize.BACKDROP),
    rating,
    genres?.map { it.toUiGenre() },
    overview
)