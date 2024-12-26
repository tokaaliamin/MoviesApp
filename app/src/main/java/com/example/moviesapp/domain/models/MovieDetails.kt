package com.example.moviesapp.domain.models


import com.example.moviesapp.domain.useCases.PostersUseCase
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetails(
    val backdropPath: String? = null,
    val genres: List<Genre>? = null,
    val id: Int? = null,
    val title: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val rating: Double? = null
)

fun MovieDetails.toUiMovieDetails() = com.example.moviesapp.ui.models.MovieDetails(
    id,
    title,
    PostersUseCase().invoke(posterPath),
    PostersUseCase().invoke(backdropPath),
    rating,
    genres?.map { it.toUiGenre() },
    overview
)