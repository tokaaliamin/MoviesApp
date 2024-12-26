package com.example.moviesapp.domain.models


import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Int? = null,
    val name: String? = null
)

fun Genre.toUiGenre() = com.example.moviesapp.ui.models.Genre(
    id,
    name
)