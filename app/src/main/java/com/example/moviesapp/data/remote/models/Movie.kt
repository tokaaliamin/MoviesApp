package com.example.moviesapp.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("title")
    val title: String? = null
)

fun Movie.toLocalMovie() = com.example.moviesapp.data.local.models.Movie(
    id ?: 0,
    posterPath,
    releaseDate,
    title,
    popularity ?: 0.0
)

fun Movie.toDomainMovie() = com.example.moviesapp.domain.models.Movie(
    id ?: 0,
    posterPath,
    releaseDate,
    title
)