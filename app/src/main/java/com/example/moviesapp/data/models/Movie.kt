package com.example.moviesapp.data.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Movie(
    @PrimaryKey
    @SerialName("id")
    val id: Int? = null,
    @ColumnInfo(name = "poster_path")
    @SerialName("poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "release_date")
    @SerialName("release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "title")
    @SerialName("title")
    val title: String? = null
)

fun Movie.toDomainMovie() = com.example.moviesapp.domain.models.Movie(
    id ?: 0,
    posterPath,
    releaseDate,
    title
)