package com.example.moviesapp.domain.models


import androidx.room.Entity
import com.example.moviesapp.domain.useCases.ImagesUseCase

@Entity
data class Movie(
    val id: Int,
    val posterSuffix: String? = null,
    val releaseDate: String? = null,
    val title: String? = null
)

fun Movie.toUiMovie(): com.example.moviesapp.ui.models.Movie {
    return com.example.moviesapp.ui.models.Movie(
        id,
        title,
        ImagesUseCase().invoke(posterSuffix, ImageSize.POSTER),
        releaseDate
    )
}