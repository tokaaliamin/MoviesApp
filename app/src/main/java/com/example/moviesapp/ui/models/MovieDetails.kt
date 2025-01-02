package com.example.moviesapp.ui.models

data class MovieDetails(
    val id: Int?,
    val title: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val rating: Double?,
    val genres: List<Genre>?,
    val description: String?
)

fun getMovieDetailsTemp(): MovieDetails {
    return MovieDetails(
        0,
        "Elemental",
        "https://image.tmdb.org/t/p/w200/4Y1WNkd88JXmGfhtWR7dmDAo1T2.jpg",
        "https://image.tmdb.org/t/p/w500/cjEcqdRdPQJhYre3HUAc5538Gk8.jpg",
        4.5,
        listOf(
            Genre(name = "Action"),
            Genre(name = "Drama"),
            Genre(name = "Fantasy"),
            Genre(name = "Adventure"),
            Genre(name = "Sci-fi"),
            Genre(name = "Action"),
            Genre(name = "Action"),
            Genre(name = "Action")
        ),
        "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance."
    )
}

