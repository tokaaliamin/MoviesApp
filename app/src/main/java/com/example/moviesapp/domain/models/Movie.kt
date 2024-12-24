package com.example.moviesapp.domain.models


import androidx.room.Entity

@Entity
data class Movie(
    val id: Int,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null
)