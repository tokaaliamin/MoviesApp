package com.example.moviesapp.features.list.data.local.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
data class Movie(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
)

fun Movie.toDomainMovie() =
    com.example.moviesapp.features.list.domain.movies.Movie(id, posterPath, releaseDate, title)