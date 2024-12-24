package com.example.moviesapp.features.list.domain.movies


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
data class Movie(
    val id: Int,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null
)