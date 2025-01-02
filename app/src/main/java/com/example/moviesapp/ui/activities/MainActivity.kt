package com.example.moviesapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.features.list.ui.components.MoviesListScreen
import com.example.moviesapp.ui.components.details.MovieDetailsScreen
import com.example.moviesapp.ui.nav.MovieDetailsDestination
import com.example.moviesapp.ui.nav.MoviesListDestination
import com.example.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = MoviesListDestination.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = MoviesListDestination.route) {
                            MoviesListScreen(onMovieClicked = { movieId ->
                                movieId?.let {
                                    navController.navigateToMovieDetails(it)
                                }
                            })
                        }

                        composable(
                            route = MovieDetailsDestination.routeWithArgs,
                            arguments = MovieDetailsDestination.arguments
                        ) { navBackStackEntry ->
                            val movieId =
                                navBackStackEntry.arguments?.getInt(MovieDetailsDestination.movieIdArg)

                            MovieDetailsScreen(movieId = movieId)
                        }
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateToMovieDetails(movieId: Int) =
    this.navigate("${MovieDetailsDestination.route}/$movieId") {
        popUpTo(
            this@navigateToMovieDetails.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }




