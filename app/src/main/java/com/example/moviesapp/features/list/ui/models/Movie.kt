package com.example.moviesapp.features.list.ui.models

data class Movie(val id: Int?, val title: String?, val posterUrl: String?, val releaseDate: String?)

fun getMovieTemp(): Movie {
    return Movie(
        0,
        "Elemental",
        "https://image.tmdb.org/t/p/w200/4Y1WNkd88JXmGfhtWR7dmDAo1T2.jpg",
        "06/16/2023"
    )
}

