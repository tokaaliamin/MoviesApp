package com.example.moviesapp.ui.nav

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface MoviesDestination {
    val route: String
}

object MoviesListDestination : MoviesDestination {
    override val route: String = "moviesList"
}

object MovieDetailsDestination : MoviesDestination {
    override val route: String = "movieDetails"
    const val movieIdArg = "movie_id"
    val routeWithArgs = "${route}/{${movieIdArg}}"
    val arguments = listOf(navArgument(movieIdArg) { type = NavType.IntType })
}
